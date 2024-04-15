package com.ucm.config;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CountingBlockingQueue<E> extends LinkedBlockingQueue<E> {
    private int taskCount = 0;

    @Override
    public void put(E e) throws InterruptedException {
        super.put(e);
        taskCount++;
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        boolean offered = super.offer(e, timeout, unit);
        if (offered) {
            taskCount++;
        }
        return offered;
    }

    @Override
    public E take() throws InterruptedException {
        E item = super.take();
        taskCount--;
        return item;
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        E item = super.poll(timeout, unit);
        if (item != null) {
            taskCount--;
        }
        return item;
    }

    public int getTaskCount() {
        return taskCount;
    }
}
