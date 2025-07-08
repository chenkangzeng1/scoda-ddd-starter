package com.scoda.ddd;

import com.scoda.ddd.component.BusFacade;
import com.scoda.ddd.model.cqrs.BaseQuery;
import com.scoda.ddd.model.cqrs.Command;
import com.scoda.ddd.model.domain.AbstractAggregateRoot;
import com.scoda.ddd.model.cqrs.handle.CommandBus;
import com.scoda.ddd.model.cqrs.handle.QueryBus;
import com.scoda.ddd.model.base.error.BaseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.scoda.ddd.component.SimpleCommandBus;
import com.scoda.ddd.component.SimpleQueryBus;

class BusFacadeTest {

    static class DummyAggregate extends AbstractAggregateRoot {}
    static class DummyCommand implements Command<DummyAggregate> {}
    static class DummyQuery extends BaseQuery<String> {}

    @Test
    void testSendCommand() throws BaseException {
        CommandBus commandBus = Mockito.mock(CommandBus.class);
        QueryBus queryBus = Mockito.mock(QueryBus.class);
        Executor executor = Runnable::run;
        DummyAggregate dummyResult = new DummyAggregate();
        when(commandBus.send(any(DummyCommand.class))).thenReturn(dummyResult);
        BusFacade busFacade = new BusFacade(commandBus, queryBus, executor);
        DummyAggregate result = busFacade.sendCommand(new DummyCommand());
        assertNotNull(result);
        assertEquals(dummyResult, result);
    }

    @Test
    void testSendAsyncCommand() throws Exception {
        CommandBus commandBus = Mockito.mock(CommandBus.class);
        QueryBus queryBus = Mockito.mock(QueryBus.class);
        Executor executor = Runnable::run;
        DummyAggregate dummyResult = new DummyAggregate();
        when(commandBus.send(any(DummyCommand.class))).thenReturn(dummyResult);
        BusFacade busFacade = new BusFacade(commandBus, queryBus, executor);
        CompletableFuture<DummyAggregate> future = busFacade.sendAsyncCommand(new DummyCommand());
        assertNotNull(future.get());
        assertEquals(dummyResult, future.get());
    }

    @Test
    void testSendQuery() throws BaseException {
        CommandBus commandBus = Mockito.mock(CommandBus.class);
        QueryBus queryBus = Mockito.mock(QueryBus.class);
        Executor executor = Runnable::run;
        String expected = "query-result";
        when(queryBus.send(any(DummyQuery.class))).thenReturn(expected);
        BusFacade busFacade = new BusFacade(commandBus, queryBus, executor);
        String result = busFacade.sendQuery(new DummyQuery());
        assertEquals(expected, result);
    }

    @Test
    void testSendCommandThrowsException() throws BaseException {
        CommandBus commandBus = Mockito.mock(CommandBus.class);
        QueryBus queryBus = Mockito.mock(QueryBus.class);
        Executor executor = Runnable::run;
        when(commandBus.send(any(DummyCommand.class))).thenThrow(new BaseException("ERR", "fail"));
        BusFacade busFacade = new BusFacade(commandBus, queryBus, executor);
        assertThrows(BaseException.class, () -> busFacade.sendCommand(new DummyCommand()));
    }

    @Test
    void testSendAsyncCommandThrowsException() throws Exception {
        CommandBus commandBus = Mockito.mock(CommandBus.class);
        QueryBus queryBus = Mockito.mock(QueryBus.class);
        Executor executor = Runnable::run;
        when(commandBus.send(any(DummyCommand.class))).thenThrow(new BaseException("ERR", "fail"));
        BusFacade busFacade = new BusFacade(commandBus, queryBus, executor);
        CompletableFuture<DummyAggregate> future = busFacade.sendAsyncCommand(new DummyCommand());
        assertThrows(Exception.class, future::get);
    }

    @Test
    void testSendTransactCommand() throws BaseException {
        CommandBus commandBus = Mockito.mock(CommandBus.class);
        QueryBus queryBus = Mockito.mock(QueryBus.class);
        Executor executor = Runnable::run;
        DummyAggregate dummyResult = new DummyAggregate();
        when(commandBus.send(any(DummyCommand.class))).thenReturn(dummyResult);
        BusFacade busFacade = new BusFacade(commandBus, queryBus, executor);
        DummyAggregate result = busFacade.sendTransactCommand(new DummyCommand());
        assertNotNull(result);
        assertEquals(dummyResult, result);
    }

    @Test
    void testSendMqCommandThrowsUnsupportedOperationException() throws BaseException {
        CommandBus commandBus = Mockito.mock(CommandBus.class);
        QueryBus queryBus = Mockito.mock(QueryBus.class);
        Executor executor = Runnable::run;
        BusFacade busFacade = new BusFacade(commandBus, queryBus, executor);
        assertThrows(UnsupportedOperationException.class, () -> busFacade.sendMqCommand(new DummyCommand()));
    }

    @Test
    void testSendAsyncCommandConcurrency() throws Exception {
        int threadCount = 20;
        CommandBus commandBus = Mockito.mock(CommandBus.class);
        QueryBus queryBus = Mockito.mock(QueryBus.class);
        ExecutorService realExecutor = Executors.newFixedThreadPool(8);
        DummyAggregate dummyResult = new DummyAggregate();
        when(commandBus.send(any(DummyCommand.class))).thenReturn(dummyResult);
        BusFacade busFacade = new BusFacade(commandBus, queryBus, realExecutor);
        List<CompletableFuture<DummyAggregate>> futures = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            futures.add(busFacade.sendAsyncCommand(new DummyCommand()));
        }
        for (CompletableFuture<DummyAggregate> future : futures) {
            assertNotNull(future.get(2, TimeUnit.SECONDS));
            assertEquals(dummyResult, future.get());
        }
        realExecutor.shutdown();
    }

    @Test
    void testSendCommandWithNull() {
        CommandBus commandBus = Mockito.mock(CommandBus.class);
        QueryBus queryBus = Mockito.mock(QueryBus.class);
        Executor executor = Runnable::run;
        BusFacade busFacade = new BusFacade(commandBus, queryBus, executor);
        assertThrows(NullPointerException.class, () -> busFacade.sendCommand(null));
    }

    @Test
    void testSimpleCommandBusSendNullThrowsException() throws BaseException {
        SimpleCommandBus bus = new SimpleCommandBus(new ArrayList<>());
        assertThrows(NullPointerException.class, () -> bus.send(null));
    }

    @Test
    void testSimpleQueryBusSendNullThrowsException() throws BaseException {
        SimpleQueryBus bus = new SimpleQueryBus(new ArrayList<>());
        assertThrows(NullPointerException.class, () -> bus.send(null));
    }

    @Test
    void testSendCommandRecursionProtection() throws BaseException {
        CommandBus commandBus = Mockito.mock(CommandBus.class);
        QueryBus queryBus = Mockito.mock(QueryBus.class);
        Executor executor = Runnable::run;
        // Simulate recursive calls: each sendCommand calls sendCommand again
        BusFacade[] facadeHolder = new BusFacade[1];
        Mockito.when(commandBus.send(any(DummyCommand.class))).thenAnswer(invocation -> {
            // Recursive call
            return facadeHolder[0].sendCommand(new DummyCommand());
        });
        BusFacade busFacade = new BusFacade(commandBus, queryBus, executor);
        facadeHolder[0] = busFacade;
        assertThrows(IllegalStateException.class, () -> busFacade.sendCommand(new DummyCommand()));
    }
}