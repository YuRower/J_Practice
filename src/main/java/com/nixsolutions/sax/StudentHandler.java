package com.nixsolutions.sax;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class StudentHandler extends DefaultHandler {
	private static Logger LOGGER = LoggerFactory.getLogger(StudentHandler.class);
	private String file;
	private Writer writer;
	private int level;
	private int indent;
	boolean isElementEvenFlag = false;
	private String startTag;
	private static final String SPACE = "   ";
	private Map<Integer, Map<String, Integer>> outerMap = new HashMap<>(); // the level of the element as a key and the
																			// map
																			// (elementName and number of its
																			// repetitions
																			// at the level ) as a value
	private Map<String, Integer> innerMap = new HashMap<>();

	private void saveToFile(String text) {
		try {
			writer.write(text);
		} catch (IOException e) {
			LOGGER.debug("Cannot write to file text{} ", text);
			throw new RuntimeException();
		}
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public void startDocument() {
		try {
			writer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			LOGGER.debug("Cannot open file {} ", file);
			throw new RuntimeException();
		}
		LOGGER.debug("Parsing started");
		saveToFile("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

	}

	private boolean isContainsOnLevel(String localName) {
		return outerMap.containsKey(level) && innerMap.containsKey(localName);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs) {
		LOGGER.debug("Process start element {} ", localName);
		if (isContainsOnLevel(localName)) {
			innerMap.put(localName, innerMap.get(localName).intValue() + 1);
			outerMap.put(level, innerMap);
		}
		indent++;
		if (isElementEvenFlag) {
			LOGGER.debug("Pass even element");
			return;
		}
		level++;
		if (!isContainsOnLevel(localName)) {
			innerMap.put(localName, 1);
			outerMap.put(level, innerMap);
		}
		if (!isEven(level, localName)) {
			saveToFile(System.lineSeparator());
			saveOpenTag(localName, qName, attrs);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		LOGGER.debug(" Element text ---> {} ", new String(ch, start, length));
		String text = new String(ch, start, length);
		if (!isElementEvenFlag && text.trim().length() > 0) {
			saveToFile(text.trim());
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		LOGGER.debug(" Process end element {}", localName);
		indent--;

		if (localName == startTag) {
			LOGGER.debug("Pass nested elements of {} ", localName);
			isElementEvenFlag = false;
			return;
		}
		if (isElementEvenFlag) {
			LOGGER.debug("Pass even element");
			return;
		}

		if (!isEven(level, localName)) {
			saveToFile("</" + localName + ">  " + System.lineSeparator());
			saveWithIndent();
		}
		level--;
	}

	private void saveWithIndent() {
		for (int i = 0; i < indent; i++) {
			saveToFile(SPACE);
		}
	}

	private void saveOpenTag(String localName, String qName, Attributes attrs) {
		saveWithIndent();
		String tagName = localName;
		saveToFile("<" + tagName);
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); i++) {
				String atrName = attrs.getLocalName(i);
				saveToFile(" " + atrName + "=\"" + attrs.getValue(i) + "\"");
			}
		}
		saveToFile(">");
	}

	private boolean isEven(int lvl, String name) {
		LOGGER.debug("name --> " + name + " amount --> " + innerMap.get(name));
		Map<String, Integer> inner = outerMap.get(lvl);
		if (inner.get(name) % 2 == 0) {
			isElementEvenFlag = true;
			startTag = name;
			return true;
		}
		return false;
	}

	@Override
	public void endDocument() {
		LOGGER.debug("\\nParsing ended ");
		if (writer != null) {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException();

			}
		}

	}

}