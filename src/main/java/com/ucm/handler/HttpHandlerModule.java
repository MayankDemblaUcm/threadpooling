package com.ucm.handler;

import com.sun.net.httpserver.HttpExchange;

import java.util.concurrent.ThreadPoolExecutor;

public class HttpHandlerModule implements com.sun.net.httpserver.HttpHandler {

    private final ThreadPoolExecutor executor;

    public HttpHandlerModule(ThreadPoolExecutor executor) {
        this.executor = executor;
    }


    @Override
    public void handle(HttpExchange exchange) {
        executor.execute(new RequestHandler(exchange, executor));
    }


}
