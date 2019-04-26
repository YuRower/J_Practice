package com.nixsolutions.dom;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ DomParserTest.class, SaxParserTest.class, })
public class JunitTester {

	public JunitTester() {
	}

	public TestSuite suite() {
		TestSuite testSuite = new TestSuite(DomParserTest.class, SaxParserTest.class);
		return testSuite;
	}
}
