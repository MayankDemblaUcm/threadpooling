package com.ucm.config;

import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {

    private final ThreadFactory defaultFactory;

    public CustomThreadFactory(ThreadFactory defaultFactory) {
        this.defaultFactory = defaultFactory;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = defaultFactory.newThread(r);
        // Log thread creation information
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 2) {
            String callerClassName = stackTrace[2].getClassName();
            String callerMethodName = stackTrace[2].getMethodName();
            String threadName = "CustomThread_" + callerClassName + "_" + callerMethodName;
            thread.setName(threadName);
            System.out.println("Thread created by: " + callerClassName + "." + callerMethodName);
            System.out.println("New thread name: " + threadName);
        }
        return thread;
    }
}
