package com.ucm.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolExecutor  extends ThreadPoolExecutor {

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        // Log thread pool and task queue information before executing the command
        logThreadPoolInfo();
        super.execute(command);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        System.out.println("Task " + r.toString() + " has been executed");
        // Log thread pool and task queue information after executing the command
      //  logThreadPoolInfo();
    }

    private void logThreadPoolInfo() {
        System.out.println("Thread pool and task queue information:");
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("| %-20s | %-15s | %-15s | %-15s |%n", "Pool Size", "Active Threads", "Queue Size", "Task Count");
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("| %-20s | %-15s | %-15s | %-15s |%n", getPoolSize(), getActiveCount(), getQueue().size(), getTaskCount());
        System.out.println("---------------------------------------------------------------------");
        System.out.println();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        System.out.println("Thread " + t.getName() + " is about to execute task " + r.toString());
    }


    @Override
    protected void terminated() {
        super.terminated();
        System.out.println("ThreadPool has terminated");
    }
}
