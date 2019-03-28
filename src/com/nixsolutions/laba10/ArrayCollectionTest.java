package com.nixsolutions.laba10;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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

        assertEquals("Size must be 1 after adding 1 element", 1,
                arrayCollection.getArray().length);

        arrayCollection.add("Two");
        assertEquals("Size must be 2 after adding null element", 2,
                arrayCollection.getArray().length);

        arrayCollection.add("Three");

        Object[] expected = arrayCollection.toArray(testArray);
        Object[] actual = arrayCollection.getArray();
        assertArrayEquals("Arrays must equals", expected, actual);

        assertTrue("add should return true if addition was done",
                arrayCollection.add(null));

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

        assertTrue("wrong containsAll",
                arrayCollection.containsAll(Arrays.asList(testArray)));

        assertFalse("wrong containsAll", arrayCollection.containsAll(
                Arrays.asList(new String[] { "One", "Two", "Three", "Four" })));

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
    public void testGetArray() {
        arrayCollection.addAll(Arrays.asList(testArray));
        Object[] actual = arrayCollection.getArray();
        assertNotNull("Can't be null", actual);
        assertArrayEquals("Arrays must equals", testArray, actual);
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
    }
}
