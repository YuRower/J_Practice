package com.nixsolutions.laba7.simple;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import interfaces.task7.simple.NamePrinterRunnable;

public class NamePrinterRunnableImpl implements  NamePrinterRunnable{
    
    public NamePrinterRunnableImpl(){}
    
    private int count ;
    private long ms ;
    private String name;
    private PrintStream stream;
    
    @Override
    public void setCount(int count) {
        if ( count <= 0) {
            throw new IllegalArgumentException();

        }
        this.count = count;        
    }

    @Override
    public void setInterval(long ms) {
        if ( ms <= 0) {
            throw new IllegalArgumentException();
        }
        this.ms = ms;        

    }

    @Override
    public void setPrintName(String name) {
        if (name== null) {
            throw new NullPointerException();
        } else if (name.length() == 0) {
            throw new IllegalArgumentException ();
        }

        this.name = name;        

    }

    @Override
    public void setStream(PrintStream stream) {
        if (stream == null) {
            throw new NullPointerException();
        }
        this.stream = stream;        

    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            stream.println(name);
            try {
                TimeUnit.MILLISECONDS.sleep(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }   
        }

    }

}
