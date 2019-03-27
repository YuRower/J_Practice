package com.nixsolutions.laba7.executor;

import java.util.concurrent.TimeUnit;

import interfaces.task7.executor.Executor;
import interfaces.task7.executor.Task;
import interfaces.task7.executor.TasksStorage;

public class ExecutorImpl implements Executor  {
    
    
    public ExecutorImpl() {}
    
    
    private TasksStorage taskStorage;
    private Thread thread ;
    private static long PRINT_INTERVAL = 100;
    private static int PRINT_COUNT = 10;
    private boolean isRunnable ;
    
    @Override
    public TasksStorage getStorage() {
        return taskStorage;
    }

    @Override
    public void setStorage( TasksStorage taskStorage) {
        this.taskStorage=taskStorage;        
    }

    @Override
    public void start() {
        if (taskStorage == null) {
            throw new NullPointerException();
        }
        if (isRunnable) {
            throw new IllegalStateException();
        }
        isRunnable = true;
        thread =  new Thread(() -> {
           while (thread.isAlive()) {
               Task task = taskStorage.get();
               try {
               if (task != null) {
                   if (!task.execute()) {
                       incCount(task);
                   }
               }
               } catch (Exception e) {
                   incCount(task);
               }
           }
        });
        thread.start();
        
        
        
    }

    private void incCount(Task task) {
        task.incTryCount();
        if ( task.getTryCount() < 5 ) {
            taskStorage.add(task);
        }
        
    }

    @Override
    public void stop() {
        if (!isRunnable) {
            throw new IllegalStateException();
        }
        interruptThread(thread);
        isRunnable= false;
    }

    @SuppressWarnings("deprecation")
    private void interruptThread(Thread thread) {
        long start = System.currentTimeMillis();
        do {
            thread.interrupt();
            try {
                TimeUnit.MILLISECONDS.sleep(1);
                if ((PRINT_INTERVAL * PRINT_COUNT * 2) + start < System
                        .currentTimeMillis()) {
                    thread.stop();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (thread.isAlive());
    }
}

