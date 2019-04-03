package com.nixsolutions.laba10;

import static org.junit.Assert.assertArrayEquals;

import java.util.NoSuchElementException;

import com.nixsolutions.laba5.ArrayCollectionImpl;

import interfaces.task5.ArrayIterator;
import junit.framework.TestCase;

public class ArrayIteratorTest extends TestCase {

    public ArrayIteratorTest() {
    }

    private String[] testArray = { "One", "Two", "Three" };
    private String[] emptyArray = {};

    ArrayCollectionImpl<String> collection = null;

    @Override
    protected void setUp() throws Exception {

        collection = new ArrayCollectionImpl<>();

    }

    public void testNotNullIterator() {
        ArrayIterator<String> it = (ArrayIterator<String>) collection
                .iterator();
        assertNotNull("Iterator can not be null", it);
    }

    public void testGetArray() {

        ArrayIterator<String> it = (ArrayIterator<String>) collection
                .iterator();

        collection.setArray(testArray);

        it = (ArrayIterator<String>) collection.iterator();
        assertTrue("Iterator must be ArrayIterator",
                it instanceof ArrayIterator);
        assertNotNull("getArray returned null after setArray", it.getArray());
        assertArrayEquals(
                "added to collection array should be equals iterator array",
                testArray, it.getArray());

    }

    public void testHasNext() {

        ArrayIterator<String> it = (ArrayIterator<String>) collection
                .iterator();
        assertFalse("Method hasNext() of empty collection must return false",
                it.hasNext());

        collection.setArray(testArray);
        assertTrue("Must has next", it.hasNext());

        collection.setArray(emptyArray);
        assertFalse("Collection has empty array", it.hasNext());
    }

    public void testNext() {

        ArrayIterator<String> it = (ArrayIterator<String>) collection
                .iterator();
        try {
            it.next();
            fail("Iterator should throw NoSuchElementException");
        } catch (NoSuchElementException elementEx) {
        } catch (Exception ex) {
            fail("Iterator should throw NoSuchElementException");
        }

        collection.setArray(testArray);

        assertNotNull("Iterator must has next", it.next());
        assertNotNull("Iterator must has next", it.next());
        assertNotNull("Iterator must has next", it.next());
        try {
            it.next();
            fail("Iterator should throw NoSuchElementException");
        } catch (NoSuchElementException elementEx) {
        } catch (Exception ex) {
            fail("should throw NoSuchElementException instead of "
                    + ex.getClass().getName());
        }
    }

    public void testRemove() {
        ArrayIterator<String> it = (ArrayIterator<String>) collection
                .iterator();
        collection.setArray(testArray);
        it = (ArrayIterator<String>) collection.iterator();
        try {
            try {
                it.remove();
                fail("should throw IllegalStateException if next not called");
            } catch (IllegalStateException e) {
            }
            it.next();
            it.next();
            it.next();
            it.remove();
            assertEquals(2, collection.size());
        } catch (UnsupportedOperationException e) {
        }
    }

    public void testArraySize() {
        ArrayIterator<String> it = (ArrayIterator<String>) collection
                .iterator();
        assertEquals("Array size should be 0 in a new collection", 0,
                it.getArray().length);

        collection.setArray(testArray);

        Object[] actual = it.getArray();

        assertEquals("Size must equals", testArray.length, actual.length);

        assertEquals("Elements should equals", testArray[0], actual[0]);
        assertEquals("Elements should equals", testArray[1], actual[1]);
        assertEquals("Elements should equals", testArray[2], actual[2]);
    }

}
