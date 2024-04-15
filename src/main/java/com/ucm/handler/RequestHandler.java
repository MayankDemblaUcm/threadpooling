package com.ucm.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ThreadPoolExecutor;

public class RequestHandler implements Runnable {

    private final HttpExchange exchange;

    private final ThreadPoolExecutor executor;

    public RequestHandler(HttpExchange exchange, ThreadPoolExecutor executor) {
        this.exchange = exchange;
        this.executor = executor;
    }

    @Override
    public void run() {

        long startTime = System.currentTimeMillis();
        Thread currentThread = Thread.currentThread();
        long threadId = currentThread.getId();
        System.out.println("Thread " + threadId + " is in state " + currentThread.getState());

        try {

            System.out.println("Generating the Report... ");
            Thread.sleep(5000);
            String response = "Report generated successfully!";
            Thread.sleep(1000);

            // Send response
            exchange.sendResponseHeaders(200, response.getBytes().length);
            // OutputStream outputStream = exchange.getResponseBody();
            // outputStream.write(response.getBytes());
            // outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Thread " + threadId + " took " + duration + " ms to execute.");

            if (executor != null) {
                // Log thread pool metrics
                int activeCount = executor.getActiveCount();
                int poolSize = executor.getPoolSize();
                int queueSize = executor.getQueue().size();
                int maxPoolSize = executor.getMaximumPoolSize();
                int availableThreads = maxPoolSize - activeCount;
                System.out.println("Thread pool metrics: Active threads: " + activeCount + ", Pool size: " + poolSize + ", Queue size: " + queueSize + ", Available threads: " + availableThreads + "\n");
            }
        }

//        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
//        long[] threadIds = threadMXBean.getAllThreadIds();
//
//        // Print table header
//        System.out.println("---------------------------------------------------------------------");
//        System.out.printf("| %-10s | %-20s | %-20s |\n", "Thread ID", "Thread State", "Source");
//        System.out.println("---------------------------------------------------------------------");
//
//        for (long id : threadIds) {
//            ThreadInfo threadInfo = threadMXBean.getThreadInfo(id);
//            if (threadInfo != null) {
//                String state = threadInfo.getThreadState().toString();
//                String source = getSource(threadInfo);
//                System.out.printf("| %-10d | %-20s | %-20s |\n", id, state, source);
//            }
//        }
//
//        System.out.println("---------------------------------------------------------------------");
    }

    private static String getSource(ThreadInfo threadInfo) {
        StackTraceElement[] stackTrace = threadInfo.getStackTrace();
        String className = "Unknown";
        if (stackTrace != null && stackTrace.length > 0) {
            for (StackTraceElement element : stackTrace) {
                className = element.getClassName();
                if (className.contains("HttpHandlerModule")) {
                    return "HttpHandlerModule";
                } else if (className.contains("RequestHandler")) {
                    return "RequestHandler";
                }
            }
            System.out.println("Source is " + className);
        }
        return className;
    }


}
