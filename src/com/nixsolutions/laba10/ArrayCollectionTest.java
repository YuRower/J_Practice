package com.nixsolutions.laba10;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import com.nixsolutions.laba5.ArrayCollectionImpl;

public class ArrayCollectionTest {

    public ArrayCollectionTest() {
    }

    private ArrayCollectionImpl<String> arrayCollection = null;
    private String[] testArray = { "One", "Two", "Three" };
    private String[] testArray2 = { "One", "Two" };
    private String[] emptyArray = {};

    @Rule
    public final Timeout timeout = new Timeout(1000, TimeUnit.MILLISECONDS);

    @Before
    public void before() throws Exception {
        arrayCollection = new ArrayCollectionImpl<>();

    }

    @Test
    public void testAdd() {
        assertTrue("Collection must modified", arrayCollection.add("One"));
        assertTrue("Method should return true", arrayCollection.add("Two"));
        arrayCollection.add("Three");
        assertArrayEquals("checking for equals of testArray and initial array",
                testArray, arrayCollection.getArray());

        assertTrue("add should return true if addition was done",
                arrayCollection.add(null));
        assertEquals("length of array should be 4 ", 4,
                arrayCollection.getArray().length);
        assertEquals("first element should be 'one'", "One",
                arrayCollection.getArray()[0]);
        int size = arrayCollection.getArray().length;
        assertNull("last element should be null",
                arrayCollection.getArray()[size - 1]);

    }

    @Test
    public void testSetGetArray() {
        assertNotNull("getArray should not return null after create",
                arrayCollection.getArray());
        arrayCollection.setArray(testArray);
        assertNotNull("getArray returned null after setArray",
                arrayCollection.getArray());
        assertArrayEquals("checking for equals of derived and initial arrays",
                testArray, arrayCollection.getArray());
    }

    @Test
    public void testClear() {
        arrayCollection.setArray(testArray);
        assertEquals("Size must be 3", 3, arrayCollection.getArray().length);
        arrayCollection.clear();
        assertEquals("Size must be 0 after cleaning", 0,
                arrayCollection.getArray().length);
    }

    @Test
    public void testIsEmpty() {
        assertTrue("New collection must be empty", arrayCollection.isEmpty());
        arrayCollection.add("Three");
        assertEquals("size must be 1 ", 1, arrayCollection.getArray().length);
        assertFalse("must be not empty after addition element",
                arrayCollection.isEmpty());

    }

    @Test
    public void testContains() {
        assertFalse("contains should return false for null after created",
                arrayCollection.contains(null));
        arrayCollection.add(null);
        assertTrue("wrong contains", arrayCollection.contains(null));
        assertEquals("size should be 1 after 1 add", 1,
                arrayCollection.getArray().length);

        arrayCollection.add(testArray[0]);

    }

    @Test(expected = NullPointerException.class)
    public void testContainsAll() {
        arrayCollection.containsAll(null);
    }

    @Test
    public void testAddAll() {
        assertTrue(" must return true after executing method ",
                arrayCollection.addAll(Arrays.asList(testArray)));
        assertNotNull("collection must be not null ",
                arrayCollection.getArray());
        assertEquals("Size must be 3", 3, arrayCollection.size());
        assertArrayEquals("checking for equals of derived and initial arrays",
                testArray, arrayCollection.getArray());

    }

    @Test
    public void testRemove() {
        arrayCollection.add(testArray[0]);
        assertTrue("сollection must modified",
                arrayCollection.remove(testArray[0]));
        assertFalse("contains removed element", Arrays
                .asList(arrayCollection.getArray()).contains(testArray[0]));

        arrayCollection.add(testArray[0]);
        arrayCollection.add(testArray[0]);
        assertFalse("should return false, if collection not changed",
                arrayCollection.remove(testArray[1]));
        assertEquals("size should not be changed", 2,
                arrayCollection.getArray().length);
        assertTrue("should return true on remove",
                arrayCollection.remove(testArray[0]));
        assertTrue("remove should remove only one elements", Arrays
                .asList(arrayCollection.getArray()).contains(testArray[0]));
    }

    @Test(expected = NullPointerException.class)
    public void setArrayNull() {
        arrayCollection.setArray(null);
    }

    @Test
    public void testArraySize() {
        assertEquals("after create size should be 0", 0,
                arrayCollection.size());
        arrayCollection.addAll(Arrays.asList(testArray));
        assertEquals("size should be 3", arrayCollection.getArray().length,
                arrayCollection.size());
        arrayCollection.clear();
        assertEquals("after clear size should be 0", 0, arrayCollection.size());

        arrayCollection.add("zero");
        assertEquals("size should be 1", 1, arrayCollection.size());

    }

    @Test
    public void testToArray() {
        arrayCollection.addAll(Arrays.asList(testArray));
        assertArrayEquals("added array and toArray method should be equals",
                testArray, arrayCollection.toArray());
    }

    @Test
    public void testRemoveAll() {
        arrayCollection.add("zero");
        arrayCollection.addAll(Arrays.asList(testArray));
        assertEquals("Size must be 4", 4, arrayCollection.getArray().length);
        assertTrue("should return true if coll changed",
                arrayCollection.removeAll(Arrays.asList(testArray)));
        assertFalse("should be not empty", arrayCollection.isEmpty());

        assertEquals("should contain one elem", 1,
                arrayCollection.getArray().length);
        assertEquals("first element should be 'zero'", "zero",
                arrayCollection.getArray()[0]);
    }

    @Test
    public void testRetainAll() throws InterruptedException {
        arrayCollection.addAll(Arrays.asList(testArray));
        assertEquals("Size must be 3", 3, arrayCollection.getArray().length);
        assertTrue("should contains all elements after addition",
                arrayCollection.containsAll(Arrays.asList(testArray)));
        List<String> list = Arrays.asList(testArray2);
        assertTrue("Collection must modified", arrayCollection.retainAll(list));
        assertEquals("After operation size must be 2", 2,
                arrayCollection.getArray().length);
        assertArrayEquals("checking for equals of derived and initial arrays",
                arrayCollection.getArray(), testArray2);
    }
}