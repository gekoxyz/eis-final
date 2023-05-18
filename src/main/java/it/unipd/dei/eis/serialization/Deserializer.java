package it.unipd.dei.eis.serialization;

import it.unipd.dei.eis.Article;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Deserializer {

  public Deserializer() {
  }

  public Article[] deserialize(String fileName) {
    Article[] articles = new Article[0];
    try {
      File xmlFile = new File(fileName);

      // Create a DocumentBuilderFactory
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

      // Create a DocumentBuilder
      DocumentBuilder builder = factory.newDocumentBuilder();

      // Parse the XML file into a Document object
      Document document = builder.parse(xmlFile);

      // Get the root element of the XML document
      Element rootElement = document.getDocumentElement();

      // Extract data from the article elements
      NodeList articleNodes = rootElement.getElementsByTagName("article");

      articles = new Article[articleNodes.getLength()];
      for (int i = 0; i < articleNodes.getLength(); i++) {
        Element articleElement = (Element) articleNodes.item(i);
        String title = getElementValue(articleElement, "title");
        String bodyText = getElementValue(articleElement, "bodyText");
        articles[i] = new Article(title, bodyText);
      }

    } catch (Exception e) {
      System.out.println("[ERROR] - Error while deserializing the XML");
      e.printStackTrace();
    }
    return articles;
  }

  private static String getElementValue(Element parentElement, String elementName) {
    NodeList nodeList = parentElement.getElementsByTagName(elementName);
    if (nodeList.getLength() > 0) {
      Node node = nodeList.item(0);
      return node.getTextContent();
    }
    return null;
  }
}
