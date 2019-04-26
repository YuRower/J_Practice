package com.nixsolutions.dom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import com.nixsolutions.AbstractXMLParser;

public class DomParser extends AbstractXMLParser {

	private static Logger LOGGER = LoggerFactory.getLogger(DomParser.class);

	private void removeEvenElement(Node node) {
		LOGGER.trace("Remove even node {} , with value{}  ", node.getNodeName(), node.getTextContent());
		Node deadNode = node.getParentNode().removeChild(node);
		LOGGER.trace("Removed node {}", deadNode);
	}

	private void printNodeAttr(Node node) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			LOGGER.trace("Node Name = " + node.getNodeName() + "; Value = " + node.getTextContent());
			if (node.hasAttributes()) {
				NamedNodeMap nodeMap = node.getAttributes();
				for (int i = 0; i < nodeMap.getLength(); i++) {
					Node tempNode = nodeMap.item(i);
					LOGGER.trace("Attr name : " + tempNode.getNodeName() + "; Value = " + tempNode.getNodeValue());
				}
			}
		}
	}

	private void visitChildNodes(NodeList nList) {
		Map<String, Integer> map = new HashMap<>(); // the name of the elemennt as a key and the number of its
													// repetitions at the level as a value
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node node = nList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				LOGGER.trace("Node Name = " + node.getNodeName() + "; Value = " + node.getTextContent());
				printNodeAttr(node);
				if (node.hasChildNodes()) {
					if (map.containsKey(node.getNodeName())) {
						map.put(node.getNodeName(), map.get(node.getNodeName()).intValue() + 1);
					} else {
						map.put(node.getNodeName(), 1);
					}
					LOGGER.trace("Visit child " + node.getNodeName() + " ");
					visitChildNodes(node.getChildNodes());
				}
				if (map.get(node.getNodeName()) % 2 == 0) {
					removeEvenElement(node);
				}
			}
		}
	}

	private void save(Document doc, File dest) throws FileNotFoundException {
		DOMImplementation impl = doc.getImplementation();
		DOMImplementationLS implLs = (DOMImplementationLS) impl.getFeature("LS", "3.0");
		LSSerializer ser = implLs.createLSSerializer();
		ser.getDomConfig().setParameter("format-pretty-print", true);
		LSOutput out = implLs.createLSOutput();
		out.setEncoding("UTF-8");
		out.setByteStream(new FileOutputStream(dest));
		ser.write(doc, out);
	}

	@Override
	public void parseAndWrite(String fileSrc, String fileDist)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DomParser d = new DomParser();
		ClassLoader classLoader = getClass().getClassLoader();
		fileSrc = classLoader.getResource(fileSrc).getPath();
		fileDist = classLoader.getResource(fileDist).getPath();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		documentBuilder = documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(new File(fileSrc));
		document.getDocumentElement().normalize();

		NodeList nodeList = document.getElementsByTagName("groups");
		d.visitChildNodes(nodeList);

		d.save(document, new File(fileDist));

	}

}
