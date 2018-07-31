package com.urls.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import com.urls.concurrent.pool.ThreadPool;

public class AsyncProcessingHandler {

	public static <B> B getProcessor(long timeOut, TimeUnit unit, Supplier<B> message)
			throws InterruptedException, ExecutionException, TimeoutException {

		CompletableFuture<B> task = CompletableFuture.supplyAsync(message, ThreadPool.INSTANCE.getThreadPool());
		return task.get(timeOut, unit);

	}

}
