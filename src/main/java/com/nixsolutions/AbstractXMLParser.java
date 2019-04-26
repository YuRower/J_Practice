package com.nixsolutions;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public abstract class AbstractXMLParser {

	public abstract void parseAndWrite(String fileSrc, String filDist)
			throws ParserConfigurationException, SAXException, IOException, TransformerException;

}
