package com.ucm.server;

import com.sun.net.httpserver.HttpServer;
import com.ucm.config.CountingBlockingQueue;
import com.ucm.config.CustomThreadFactory;
import com.ucm.config.CustomThreadPoolExecutor;
import com.ucm.handler.HttpHandlerModule;

import java.net.InetSocketAddress;
import java.util.concurrent.*;

public class ServerModule {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Create a thread pool with a custom thread factory
        ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
        ThreadFactory customThreadFactory = new CustomThreadFactory(defaultThreadFactory);

        // Create a ThreadPoolExecutor with the custom ThreadFactory
        int corePoolSize = 5; // Set your desired core pool size
        int maxPoolSize = 10; // Set your desired maximum pool size
        long keepAliveTime = 60; // Set your desired keep alive time (in seconds)
        TimeUnit timeUnit = TimeUnit.SECONDS; // Set your desired time unit for keep alive time
        CountingBlockingQueue<Runnable> workQueue = new CountingBlockingQueue<>(); // Set your desired work queue implementation and capacity

        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, timeUnit, workQueue);

        // Create and configure your HttpServer
        server.createContext("/", new HttpHandlerModule(executor));
        server.setExecutor(executor);

        // Start your server
        server.start();
        System.out.println("Server started on port - " + PORT);

    }


}
