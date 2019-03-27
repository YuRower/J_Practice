package com.nixsolutions.laba8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import interfaces.task8.CyclicCollection;
import interfaces.task8.CyclicItem;

public class CyclicCollectionImpl implements CyclicCollection, Serializable {

    /**
     * 
     */
    public CyclicCollectionImpl() {
    }

    private static final long serialVersionUID = -9132311961336653711L;
    private CyclicItem head;
    private CyclicItem tail;
    private int size;

    @Override
    public int size() {
        return size;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private boolean isContains(CyclicItem item) {
        if (isEmpty()) {
            return false;
        }
        CyclicItem curr = head;
        do {
            if (curr.equals(item)) {
                return true;
            } else {
                curr = curr.nextItem();
            }
        } while (curr != head);
        return false;
    }

    @Override
    public CyclicItem getFirst() {
        return head;
    }

    @Override
    public boolean add(CyclicItem cyclicItem) {
        if (cyclicItem == null) {
            throw new NullPointerException();
        } else if (head == null) {
            head = cyclicItem;
            tail = cyclicItem;
            cyclicItem.setNextItem(cyclicItem);
            size++;
            return true;
        } else if (isContains(cyclicItem)) {
            throw new IllegalArgumentException();
        }
        tail.setNextItem(cyclicItem);
        cyclicItem.setNextItem(head);
        tail = cyclicItem;
        size++;
        return true;
    }

    @Override
    public void insertAfter(CyclicItem cyclicItem, CyclicItem newItem) {
        if (cyclicItem == null || newItem == null) {
            throw new NullPointerException();
        } else if (!isContains(cyclicItem) || isContains(newItem)) {
            throw new IllegalArgumentException();
        }

        if (cyclicItem.equals(head)) {
            newItem.setNextItem(head.nextItem());
            head.setNextItem(newItem);
            size++;
            return;
        }

        if (cyclicItem.equals(tail)) {
            tail.setNextItem(newItem);
            newItem.setNextItem(head);
            tail = newItem;
            size++;
            return;
        }
        CyclicItem curItem = head.nextItem();
        while (curItem != head) {
            if (curItem.equals(cyclicItem)) {

                cyclicItem.setNextItem(newItem);
                newItem.setNextItem(cyclicItem.nextItem());
                size++;
                break;
            } else {
                curItem = curItem.nextItem();
            }

            size++;
        }
    }

    @Override
    public boolean remove(CyclicItem cyclicItem) {
        boolean modified = false;
        if (cyclicItem == null) {
            throw new NullPointerException();
        }
        if (head == null) {
            return false;
        } else if (head.equals(cyclicItem)) {
            head = head.nextItem();
            size--;
            modified = true;
        } else {
            CyclicItem curItem = head.nextItem();
            CyclicItem prevItem = head;
            while (curItem != head) {
                if (cyclicItem.equals(curItem)) {
                    prevItem.setNextItem(curItem.nextItem());
                    size--;
                    modified = true;
                    break;
                }
                prevItem = curItem;
                curItem = curItem.nextItem();
            }
            if (head.equals(tail)) {
                head = tail = null;
            } else if (cyclicItem.equals(tail)) {
                tail = prevItem;
            }
        }
        if (size == 0) {
            head = null;
        }

        return modified;
    }

    @Override
    public String toString() {
        String res;
        res = head.toString();
        CyclicItem curItem = head.nextItem();
        while (curItem != head) {
            res += curItem.toString();
            curItem = curItem.nextItem();
        }
        return res;
    }

    public static void main(String[] args) {
        try {
            CyclicCollectionImpl collection = new CyclicCollectionImpl();
            collection.add(new CyclicItemImpl(1));
            collection.add(new CyclicItemImpl(2));
            collection.add(new CyclicItemImpl(3));
            collection.add(new CyclicItemImpl(4));
            System.out.println(collection.getFirst());
            System.out.println(collection.remove(new CyclicItemImpl(4)));
            collection.insertAfter(new CyclicItemImpl(3),
                    new CyclicItemImpl(5));

            System.out.println(collection);

            CyclicItem ci2 = new CyclicItemImpl(334, 2);

            SerializableUtilsImpl s = new SerializableUtilsImpl();
            try (OutputStream outputStream = new FileOutputStream(
                    new File("temp1.txt"))) {
                s.serialize(outputStream, ci2);
                outputStream.flush();
            }
            try (InputStream inputStream = new FileInputStream(
                    new File("temp1.txt"));
                    InputStream inputStream1 = new FileInputStream(
                            new File("temp1.txt"))) {
                CyclicItem c = (CyclicItem) s.deserialize(inputStream);
                CyclicItem c1 = (CyclicItem) s.deserialize(inputStream1);

                System.out.println(c == c1);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}