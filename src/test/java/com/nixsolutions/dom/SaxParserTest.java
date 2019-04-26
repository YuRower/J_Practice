package com.nixsolutions.dom;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isSimilarTo;
import static org.xmlunit.matchers.HasXPathMatcher.hasXPath;

import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;

import com.nixsolutions.AbstractXMLParser;
import com.nixsolutions.sax.SaxParser;

public class SaxParserTest {
	private AbstractXMLParser parser;
	private String FILE_RESULT = "students_output.xml";
	private String FILE_SAX_RESULT = "students_SAX_result.xml";
	private String FILE_INVALID = "students_invalid.xml";
	private String FILE_SRC = "students.xml";

	@Before
	public void setUp() throws Exception {
		parser = new SaxParser();
	}

	@Test
	public void testParseSax() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		parser.parseAndWrite(FILE_SRC, FILE_RESULT);
		ClassLoader classLoader = getClass().getClassLoader();
		String srcPath = classLoader.getResource(FILE_RESULT).getPath();
		String testPath = classLoader.getResource(FILE_SAX_RESULT).getPath();
		assertThat(Input.fromFile(srcPath), isSimilarTo(Input.fromFile(testPath)));
	}

	@Test
	public void testParseSax_WithInvalidResult()
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		parser.parseAndWrite(FILE_SRC, FILE_RESULT);
		ClassLoader classLoader = getClass().getClassLoader();
		String controlXml = classLoader.getResource(FILE_RESULT).getPath();
		String testXml = classLoader.getResource(FILE_INVALID).getPath();

		Diff myDiff = DiffBuilder.compare(Input.fromFile(controlXml)).withTest(Input.fromFile(testXml)).build();

		Iterator<Difference> iter = myDiff.getDifferences().iterator();
		int size = 0;
		while (iter.hasNext()) {
			System.out.println(iter.next().toString());
			size++;
		}
		assertThat(size, greaterThan(1));
	}

	@Test
	public void testXPathSrcFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		String testPath = classLoader.getResource(FILE_SRC).getPath();
		assertThat(Input.fromFile(testPath), hasXPath("//groups"));
		assertThat(Input.fromFile(testPath), hasXPath("//group"));
		assertThat(Input.fromFile(testPath), hasXPath("//students"));
		assertThat(Input.fromFile(testPath), hasXPath("//@login"));
		assertThat(Input.fromFile(testPath), not(hasXPath("//studdent")));
	}
}
