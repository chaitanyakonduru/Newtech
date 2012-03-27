
package com.logictree.supply.network;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Srinivas
 */
public class NamedThreadFactory implements ThreadFactory
{
    protected final String id;    
    protected final AtomicInteger n = new AtomicInteger(1);


    public NamedThreadFactory(String id)
    {
    	/*Executors executors;
    	executors.newFixedThreadPool(nThreads)*/
    	super();
        this.id = id;
        
    }


    public Thread newThread(Runnable runnable)
    {
        String name = id + ":" + n.getAndIncrement();
        return new Thread(runnable, name);
        
    }

}
