package it.unipd.dei.eis.serialization;

import it.unipd.dei.eis.Article;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

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
      FileInputStream fileInputStream = new FileInputStream(fileName);
      BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
      XMLDecoder xmlDecoder = new XMLDecoder(bufferedInputStream);
      articles = (Article[]) xmlDecoder.readObject();
      xmlDecoder.close();
      bufferedInputStream.close();
      fileInputStream.close();

      System.out.println("[INFO] - Deserialized " + articles.length + articles);
    } catch (IOException e) {
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
