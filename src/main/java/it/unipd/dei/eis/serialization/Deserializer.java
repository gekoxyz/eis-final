package it.unipd.dei.eis.serialization;

import it.unipd.dei.eis.Article;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * The Deserializer class is responsible for deserializing XML files and extracting article data from them.
 */
public class Deserializer {

  /**
   * Deserialize xml file and extract article data
   *
   * @param fileName name of the file to deserialize
   * @return an array of Article objects extracted from the XML file
   */
  public Article[] deserialize(String fileName) {
    Article[] articles = new Article[0];
    try {
      // Parse the XML file into a Document object
      Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);
      // Get the root element of the XML document
      Element rootElement = document.getDocumentElement();
      // Extract data from the article elements
      NodeList articleNodes = rootElement.getElementsByTagName("article");
      articles = new Article[articleNodes.getLength()];
      System.out.println("ARTICLE NODES LENGHT " + articleNodes.getLength());
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

  /**
   * Get the value of the specified element from the parent element.
   *
   * @param parentElement the parent element containing the desired element
   * @param elementName   the name of the element to retrieve the value from
   * @return the value of the specified element, or null if not found
   */
  private static String getElementValue(Element parentElement, String elementName) {
    NodeList nodeList = parentElement.getElementsByTagName(elementName);
    if (nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    }
    return null;
  }
}
