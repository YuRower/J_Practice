package com.nixsolutions.laba5;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import interfaces.task5.ArrayCollection;
import interfaces.task5.ArrayIterator;

public class ArrayCollectionImpl<T> implements ArrayCollection<T> {

    public ArrayCollectionImpl() {
    }

    @SuppressWarnings("unchecked")
    private T[] array = (T[]) new Object[0];

    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {

        if (size == 0) {
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == o) {
                return true;
            } else if (array[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIteratorImpl();
    }

    @Override
    public Object[] toArray() {
        @SuppressWarnings("unchecked")
        T[] newArray = (T[]) new Object[this.size];
        System.arraycopy(array, 0, newArray, 0, this.size);
        return newArray;
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(T e) {
        if (size == array.length) {
            @SuppressWarnings("unchecked")
            T[] newArray = (T[]) new Object[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < array.length; i++) {
            if (o.equals(array[i])) {
                array[i] = null;
                System.arraycopy(array, i + 1, array, i, this.size - i - 1);
                size--;
                array = Arrays.copyOf(array, size);
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        for (Object item : c) {
            if (!this.contains(item)) {
                return false;
            }
        }
        return true;

    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c == this) {
            throw new IllegalArgumentException();
        }
        Object[] copy = c.toArray();
        Object[] temp = Arrays.copyOf(array, array.length + copy.length);
        System.arraycopy(copy, 0, temp, array.length, copy.length);
        array = (T[]) temp;
        size += copy.length;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        int siezModification = size();
        for (Object item : c) {
            remove(item);
        }
        return siezModification != size();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int sizeModification = size();
        if (c == null) {
            throw new NullPointerException();
        }
        for (Object item : this) {
            if (!c.contains(item)) {
                remove(item);
            }

        }
        return sizeModification != size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        array = (T[]) new Object[0];
        size = 0;
    }

    @Override
    public Object[] getArray() {
        return array;
    }

    @Override
    public void setArray(T[] arr) {
        if (arr == null) {
            throw new NullPointerException();
        }
        this.size = arr.length;
        this.array = arr;

    }

    @Override
    public String toString() {
        return "ArrayCollectionImpl [array=" + Arrays.toString(array)
                + ", size=" + size + "]";
    }

    private class ArrayIteratorImpl implements ArrayIterator<T> {
        private int cursor;

        @Override
        public boolean hasNext() {
            return ArrayCollectionImpl.this.size() > cursor;
        }

        @Override
        public T next() {
            if (cursor >= array.length) {
                throw new NoSuchElementException();
            }
            return ArrayCollectionImpl.this.array[cursor++];
        }

        @Override
        public Object[] getArray() {
            return ArrayCollectionImpl.this.array;
        }

    }

}
