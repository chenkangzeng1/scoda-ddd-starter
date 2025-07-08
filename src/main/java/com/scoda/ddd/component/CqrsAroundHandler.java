package com.scoda.ddd.component;

import com.alibaba.fastjson.JSON;
import com.scoda.ddd.model.cqrs.BaseCommand;
import com.scoda.ddd.model.cqrs.BaseQuery;
import com.scoda.ddd.model.cqrs.handle.EventBus;
import com.scoda.ddd.model.domain.AbstractDomainEvent;
import com.scoda.ddd.model.domain.DomainEvent;
import com.scoda.ddd.model.domain.DomainEvents;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CQRS aspect, uniformly intercepts command, query, and event handlers, records link logs and automatically publishes domain events.
 *
 * @author kangzeng.ckz
 * @since 2025/7/3
 **/
@Aspect
public class CqrsAroundHandler {
    private final EventBus eventBus;
    private Logger logger = LoggerFactory.getLogger(CqrsAroundHandler.class);

    /**
     * Constructor for CqrsAroundHandler.
     * @param eventBus the event bus
     */
    public CqrsAroundHandler(EventBus eventBus) {
        this.eventBus = eventBus;
        logger.info("[CqrsAroundHandler] Bean created!");
    }

    /**
     * Around advice for command, query, and event handlers.
     * @param point the join point
     * @return the result of method execution
     * @throws Throwable if method execution fails
     */
    @Around("execution(* com.scoda.ddd.model.cqrs.handle.CommandHandler+.handle(..)) || " +
            "execution(* com.scoda.ddd.model.cqrs.handle.QueryHandler+.handle(..)) || " +
            "execution(* com.scoda.ddd.model.cqrs.handle.EventHandler+.handle(..))")
    public Object handleExecutor(ProceedingJoinPoint point) throws Throwable {
        logger.info("[CqrsAroundHandler] handleExecutor invoked! method: {}", point.getSignature());
        long timestamp = System.currentTimeMillis();
        Object[] args = point.getArgs();
        Object firstArg = (args != null && args.length > 0) ? args[0] : null;
        String username = null;
        String requestId = "";

        if (firstArg instanceof BaseCommand) {
            requestId = ((BaseCommand) firstArg).getRequestId();
            username = ((BaseCommand) firstArg).getUserName();
        } else if (firstArg instanceof BaseQuery) {
            requestId = ((BaseQuery) firstArg).getRequestId();
            username = ((BaseQuery) firstArg).getUserName();
        } else if (firstArg instanceof AbstractDomainEvent) {
            requestId = ((AbstractDomainEvent) firstArg).getRequestId();
            username = ((AbstractDomainEvent) firstArg).getUserName();
        }
        Object result;
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
        long timestamp1 = System.currentTimeMillis();
        logger.info("requestId:{},method:{}-{}, {}:{}, result:{}, duration:{}-{},username:{}", requestId, point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName(), firstArg.getClass().getSimpleName(), JSON.toJSONString(firstArg), JSON.toJSONString(result), (System.currentTimeMillis() - timestamp),
                (System.currentTimeMillis() - timestamp1), username);
        return result;
    }

    /**
     * Around advice for command bus send method.
     * @param joinPoint the join point
     * @return the result of method execution
     * @throws Throwable if method execution fails
     */
    @Around("execution(* com.scoda.ddd.model.cqrs.handle.CommandBus+.send(..))")
    public Object handleEventBus(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("[CqrsAroundHandler] handleEventBus invoked! method: {}", joinPoint.getSignature());
        // 执行原始命令
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
        // 获取命令对象
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof BaseCommand) {
            // 如果命令执行后产生了领域事件，发布事件
            if (result instanceof DomainEvents) {
                for (AbstractDomainEvent event : ((DomainEvents) result).getDomainEvents()) {
                    sycBaseInfo((BaseCommand) args[0], event);
                    eventBus.publish(event);
                }
            }
            // 如果结果本身就是一个DomainEvent，直接发布
            else if (result instanceof DomainEvent) {
                eventBus.publish((DomainEvent) result);
            }
        }
        return result;
    }

    private void sycBaseInfo(BaseCommand command, AbstractDomainEvent event) {
        event.setRequestId(command.getRequestId());
        event.setJti(command.getJti());
        event.setAuthorities(command.getAuthorities());
        event.setUserName(command.getUserName());
        event.setCallerUid(command.getCallerUid());
    }
}