package com.scoda.ddd.component;

import com.scoda.ddd.model.cqrs.handle.CommandBus;
import com.scoda.ddd.model.cqrs.handle.CommandHandler;
import com.scoda.ddd.model.cqrs.handle.EventBus;
import com.scoda.ddd.model.cqrs.handle.EventHandler;
import com.scoda.ddd.model.cqrs.handle.QueryBus;
import com.scoda.ddd.model.cqrs.handle.QueryHandler;
import com.scoda.ddd.model.domain.DomainEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import java.util.List;

/**
 * DDD/CQRS bus auto-configuration class, registers core buses, facades, aspects, thread pools and other Beans, supports business custom overrides.
 *
 * @author kangzeng.ckz
 * @since 2025/7/3
 **/
@Configuration
@EnableConfigurationProperties(AsyncConfig.class)
public class ScodaDddBusAutoConfiguration {
    /**
     * Creates a CQRS around handler.
     * @param eventBus the event bus
     * @return the CQRS around handler
     */
    @Bean
    public CqrsAroundHandler cqrsAroundHandler(EventBus eventBus) {
        return new CqrsAroundHandler(eventBus);
    }

    /**
     * Creates a simple event bus.
     * @param eventHandlers the event handlers
     * @return the simple event bus
     */
    @Bean
    public EventBus simpleEventBus(List<EventHandler<? extends DomainEvent>> eventHandlers) {
        return new SimpleEventBus(eventHandlers);
    }

    /**
     * Creates a simple command bus.
     * @param commandHandlers the command handlers
     * @return the simple command bus
     */
    @Bean
    public CommandBus simpleCommandBus(List<CommandHandler<?, ?>> commandHandlers) {
        return new SimpleCommandBus(commandHandlers);
    }

    /**
     * Creates a simple query bus.
     * @param queryHandlers the query handlers
     * @return the simple query bus
     */
    @Bean
    public QueryBus simpleQueryBus(List<QueryHandler<?, ?>> queryHandlers) {
        return new SimpleQueryBus(queryHandlers);
    }

    /**
     * Creates a CQRS async executor.
     * @param asyncConfig the async configuration
     * @return the CQRS async executor
     */
    @Bean("cqrsAsyncExecutor")
    @ConditionalOnMissingBean(name = "cqrsAsyncExecutor")
    public Executor cqrsAsyncExecutor(AsyncConfig asyncConfig) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncConfig.getCorePoolSize());
        executor.setMaxPoolSize(asyncConfig.getMaxPoolSize());
        executor.setQueueCapacity(asyncConfig.getQueueCapacity());
        executor.setThreadNamePrefix(asyncConfig.getThreadNamePrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * Creates a bus facade.
     * @param commandBus the command bus
     * @param queryBus the query bus
     * @param cqrsAsyncExecutor the CQRS async executor
     * @return the bus facade
     */
    @Bean
    @ConditionalOnMissingBean
    public BusFacade busFacade(CommandBus commandBus, QueryBus queryBus, Executor cqrsAsyncExecutor) {
        return new BusFacade(commandBus, queryBus, cqrsAsyncExecutor);
    }
} 