package ru.savinov.multithreading;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CF01MultithreadingHistoryTest {

    int result;

    @Test
    public void foo() {
        System.out.println("");
    }

    // java 1 - 4
    @Test
    public void testStartJoin() throws InterruptedException {
        Thread thread = new Thread(() -> {
            result = slowInit();
        });
        thread.start();
        thread.join();
        System.out.println(String.format("Execution testStartJoin() result %s", result));
    }

    // java 5
    @Test
    public void testFuture() throws ExecutionException, InterruptedException {
        Callable<Integer> callable = () -> slowInit();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<Integer> future = executorService.submit(callable);

        Integer result = future.get();

        System.out.println(String.format("Execution testFuture() result %s", result));
    }

    // java 8
    @Test
    public void testCompletableFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> slowInit());
        Integer result = future.get();
        System.out.println(String.format("Execution testCompletableFuture() result %s", result));

    }

    @Test
    public void promiseNextInt() {
        CompletableFuture.supplyAsync(() -> slowInit())
                .thenAccept(res -> System.out.println(res))
                .thenRun(() -> System.out.println("is done"));
        System.out.println("Execution promiseNextInt()");
    }

    @Test
    public void incrementInt() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> slowInit())
                .thenApply((i) -> slowIncrement(i))
                .thenApply((i) -> slowIncrement(i))
                .thenAccept((res) -> System.out.println("conveyor with value: " + res));

        voidCompletableFuture.get();
        long endTime = System.currentTimeMillis();
        System.out.println("Execution incrementInt(): " + (endTime - startTime));
    }

    public int slowInit() {
        System.out.println("started task slowInit()");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int slowIncrement(Integer i) {
        i++;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("finished task slowIncrement() with value: %s", i));
        return i;
    }
}
