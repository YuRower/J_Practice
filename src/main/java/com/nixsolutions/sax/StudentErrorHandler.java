package com.nixsolutions.sax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class StudentErrorHandler implements ErrorHandler {

	private Logger LOGGER = LoggerFactory.getLogger(StudentErrorHandler.class);

	@Override
	public void warning(SAXParseException e) {
		LOGGER.warn(getLineAddress(e) + "-" + e.getMessage());
	}

	@Override
	public void error(SAXParseException e) {
		LOGGER.error(getLineAddress(e) + " - " + e.getMessage());
	}

	@Override
	public void fatalError(SAXParseException e) {
		LOGGER.error(getLineAddress(e) + " - " + e.getMessage());
	}

	private String getLineAddress(SAXParseException e) {
		return e.getLineNumber() + " : " + e.getColumnNumber();
	}
}