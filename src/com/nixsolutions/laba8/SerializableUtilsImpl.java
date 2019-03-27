package com.nixsolutions.laba8;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import interfaces.task8.SerializableUtils;

public class SerializableUtilsImpl implements SerializableUtils {

    public SerializableUtilsImpl() {
    }

    @Override
    public void serialize(OutputStream outputStream, Object object) {
        if (outputStream == null || object == null) {
            throw new NullPointerException();
        }
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(outputStream);
            objectOut.writeObject(object);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Object deserialize(InputStream inputStream) {
        if (inputStream == null) {
            throw new NullPointerException();
        }
        try {
            ObjectInputStream objectIn = new ObjectInputStream(inputStream);
            return objectIn.readObject();
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
