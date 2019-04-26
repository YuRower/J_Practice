package com.nixsolutions.sax;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SaxParser extends com.nixsolutions.AbstractXMLParser {

	@Override
	public void parseAndWrite(String fileSrc, String fileDist)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		XMLReader reader = XMLReaderFactory.createXMLReader();
		StudentHandler handler = new StudentHandler();
		ClassLoader classLoader = getClass().getClassLoader();
		fileSrc = classLoader.getResource(fileSrc).getPath();
		fileDist = classLoader.getResource(fileDist).getPath();
		reader.setContentHandler(handler);
		handler.setFile(fileDist);
		reader.setErrorHandler(new StudentErrorHandler());
		reader.parse(fileSrc);
	}

}
