package com.logictree.supply.network;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * @author Srinivas
 */
public class RequestThreadPoolExecutor extends ThreadPoolExecutor {

	public RequestThreadPoolExecutor(String name) {
		this(1, 8, Integer.MAX_VALUE, TimeUnit.SECONDS, 
				new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory(name));
	}
	
	public RequestThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
	}
}
