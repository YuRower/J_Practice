package com.nixsolutions.laba7.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import interfaces.task7.executor.Task;
import interfaces.task7.executor.TasksStorage;

public class TasksStorageImpl implements TasksStorage {

    public TasksStorageImpl() {
    }

    private BlockingQueue<Task> queue = new LinkedBlockingQueue<>();;

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException();
        }
        queue.offer(task);
    }

    @Override
    public int count() {
        return queue.size();
    }

    @Override
    public Task get() {
        return queue.poll();
    }

}
