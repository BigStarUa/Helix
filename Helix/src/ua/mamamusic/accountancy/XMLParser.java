package ua.mamamusic.accountancy;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	private Document parseXmlFile(File file) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom;
		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			dom = db.parse(file);
			return dom;

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
	
	public XlsRule parseDocument(File file){
		Document dom = parseXmlFile(file);
		//get the root element
		Element docEle = dom.getDocumentElement();

		//get a nodelist of elements
		NodeList nl = docEle.getElementsByTagName("Rule");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {

				//get the employee element
				Element el = (Element)nl.item(i);

				//get the Employee object
				XlsRule rule = getXlsRule(el);
				return rule;
			}
		}
		return null;
	}
	
	private XlsRule getXlsRule(Element el){
		
		int count = getIntValue(el,"columnCount");
		int id = getIntValue(el,"columnId");
		int price = getIntValue(el,"columnPrice");

		String type = el.getAttribute("type");

		//Create a new Employee with the value read from the xml nodes
		XlsRule rule = new XlsRule(count,id,price);
		return rule;
	}
	
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
	
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	
	
}
