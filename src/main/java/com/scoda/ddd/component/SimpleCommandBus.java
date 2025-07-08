package com.scoda.ddd.component;

import com.scoda.ddd.model.base.error.BaseErrorCode;
import com.scoda.ddd.model.base.error.BaseException;
import com.scoda.ddd.model.cqrs.Command;
import com.scoda.ddd.model.cqrs.handle.CommandBus;
import com.scoda.ddd.model.cqrs.handle.CommandHandler;
import com.scoda.ddd.model.domain.AbstractAggregateRoot;
import org.springframework.core.GenericTypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of command bus, automatically registers and dispatches command handlers based on thread-safe Map, supports monitoring and exception handling.
 *
 * @author kangzeng.ckz
 * @since 2024/10/27
 **/
public class SimpleCommandBus implements CommandBus {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCommandBus.class);
    private final Map<Class<? extends Command>, CommandHandler<?, ?>> handlers = new ConcurrentHashMap<>();

    /**
     * Constructor for SimpleCommandBus.
     * @param commandHandlers the list of command handlers
     */
    public SimpleCommandBus(List<CommandHandler<?, ?>> commandHandlers) {
        logger.info("[SimpleCommandBus] Constructor called, handlers size: {}", commandHandlers.size());
        for (CommandHandler<?, ?> handler : commandHandlers) {
            Class<?>[] typeArguments = GenericTypeResolver.resolveTypeArguments(
                    handler.getClass(),
                    CommandHandler.class
            );
            if (typeArguments != null && typeArguments.length > 0) {
                Class<? extends Command> commandType = (Class<? extends Command>) typeArguments[0];
                handlers.put(commandType, handler);
                logger.info("[SimpleCommandBus] Registered handler for command: {}", commandType.getName());
            }
        }
        logger.info("[SimpleCommandBus] Registered {} command handlers", handlers.size());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends AbstractAggregateRoot> R send(Command<R> command) throws BaseException {
        if (command == null) {
            throw new NullPointerException("command must not be null");
        }
        logger.info("[SimpleCommandBus] send called for command: {}", command.getClass().getName());
        CommandHandler<Command<R>, R> handler = (CommandHandler<Command<R>, R>) handlers.get(command.getClass());
        if (handler == null) {
            throw new BaseException(BaseErrorCode.BUS_CLASS_NOT_FOUND_ERROR.getCode(),
                    "No handler registered for command: " + command.getClass().getName());
        }
        R result = handler.handle(command);
        return result;
    }
}