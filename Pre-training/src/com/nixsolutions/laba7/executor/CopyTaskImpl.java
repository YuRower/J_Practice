package com.nixsolutions.laba7.executor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import interfaces.task7.executor.CopyTask;

public class CopyTaskImpl implements CopyTask {

    public CopyTaskImpl() {
    }

    private FileInputStream in;
    private FileOutputStream out;

    private int count;

    @Override
    public boolean execute() throws Exception {
        try (FileChannel inChannel = in.getChannel();
                final FileChannel outChannel = out.getChannel()) {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            in.close();
            out.close();
        }
        return true;
    }

    @Override
    public int getTryCount() {
        return count;
    }

    @Override
    public void incTryCount() {
        count++;
    }

    @Override
    public void setDest(String dest) {
        if (dest == null) {
            throw new NullPointerException();
        }
        try {
            out = new FileOutputStream(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setSource(String source) {
        if (source == null) {
            throw new NullPointerException();
        }
        try {
            in = new FileInputStream(source);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);

        }
    }

    public static void main(String[] args) {
        CopyTaskImpl c = new CopyTaskImpl();
        c.setSource("temp2.txt");
        c.setDest("temp1.txt");
        try {
            c.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
