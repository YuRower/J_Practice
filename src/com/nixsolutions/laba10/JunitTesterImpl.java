package com.nixsolutions.laba10;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import interfaces.junit.JunitTester;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ArrayIteratorTest.class, ArrayCollectionTest.class, })
public class JunitTesterImpl implements JunitTester {

    public JunitTesterImpl() {
    }

    @Override
    public TestSuite suite() {
        TestSuite testSuite = new TestSuite(ArrayIteratorTest.class,
                ArrayCollectionTest.class);
        // testSuite.addTest(new JUnit4TestAdapter(ArrayCollectionTest.class));
        return testSuite;
    }
}