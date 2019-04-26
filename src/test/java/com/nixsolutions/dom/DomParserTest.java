package com.nixsolutions.dom;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationProblem;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;

import com.nixsolutions.AbstractXMLParser;

public class DomParserTest {
	private AbstractXMLParser parser;
	private String STUDENT_SHEMA = "students.xsd";
	private String FILE_RESULT = "students_output.xml";
	private String FILE_DOM_RESULT = "students_DOM_result.xml";
	private String FILE_INVALID = "students_invalid.xml";
	private String FILE_SRC = "students.xml";

	@Before
	public void setUp() throws Exception {
		parser = new DomParser();
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

	@Test
	public void testIsSrcFileValid()
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
		v.setSchemaSource(Input.fromStream(DomParserTest.class.getResourceAsStream("/" + STUDENT_SHEMA)).build());
		ValidationResult r = v.validateInstance(
				Input.fromStream(DomParserTest.class.getResourceAsStream("/" + FILE_INVALID)).build());
		assertFalse(r.isValid());
	}

	@Test
	public void testParseDOM() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		parser.parseAndWrite(FILE_SRC, FILE_RESULT);
		ClassLoader classLoader = getClass().getClassLoader();
		String srcPath = classLoader.getResource(FILE_RESULT).getPath();
		String testPath = classLoader.getResource(FILE_DOM_RESULT).getPath();
		assertThat(Input.fromFile(srcPath), isSimilarTo(Input.fromFile(testPath)));
	}

	@Test
	public void testParseDOM_WithInvalidResult()
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
	public void testIsTestFileInvalid()
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		parser.parseAndWrite(FILE_SRC, FILE_RESULT);

		Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
		v.setSchemaSource(Input.fromStream(DomParserTest.class.getResourceAsStream("/" + STUDENT_SHEMA)).build());
		ValidationResult r = v
				.validateInstance(Input.fromStream(DomParserTest.class.getResourceAsStream("/" + FILE_SRC)).build());
		Iterator<ValidationProblem> probs = r.getProblems().iterator();
		while (probs.hasNext()) {
			System.out.println(probs.next().toString());
		}
		assertTrue(r.isValid());
	}

}
