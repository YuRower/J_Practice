package com.nixsolutions.laba8;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import interfaces.task8.PathClassLoader;

public class PathClassLoaderImpl extends ClassLoader
        implements PathClassLoader {

    public PathClassLoaderImpl() {
    }

    private String dir;

    @Override
    public String getPath() {
        return dir;
    }

    @Override
    public void setPath(String dir) {
        if (dir == null) {
            throw new NullPointerException();
        }
        this.dir = dir;
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            byte bytes[] = loadClassFromFile(dir + "/" + className + ".class");
            return defineClass(className, bytes, 0, bytes.length);
        } catch (FileNotFoundException ex) {
            return super.findClass(className);
        } catch (IOException ex) {
            return super.findClass(className);
        }
    }

    private byte[] loadClassFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] buffer;

        try (InputStream in = new FileInputStream(file)) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = 0;

            while ((nextValue = in.read()) != -1) {
                byteStream.write(nextValue);
            }

            buffer = byteStream.toByteArray();

        } catch (Exception e) {
            throw new FileNotFoundException();
        }
        return buffer;
    }

}
