package com.url.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum ThreadPool {

	INSTANCE;

	private ExecutorService executorService = null;

	private ThreadPool() {
		executorService  = Executors.newCachedThreadPool();
	}

	public ExecutorService getThreadPool() {
		return executorService;
	}
}
