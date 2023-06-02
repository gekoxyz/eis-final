package it.unipd.dei.eis.serialization;

import it.unipd.dei.eis.Article;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;


public class Serializer {

  /**
   * Serialize article data to the xml file
   *
   * @param articlesList the articles to serialize
   * @param fileName     the output file name
   */
  public void serialize(Article[] articlesList, String fileName) {
    try {
      Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

      // Create the root element
      Element rootElement = document.createElement("articles");
      document.appendChild(rootElement);

      // Create article elements for each article object
      for (Article article : articlesList) {
        Element articleElement = document.createElement("article");
        rootElement.appendChild(articleElement);

        // Add title element
        Element titleElement = document.createElement("title");
        titleElement.appendChild(document.createTextNode(article.getTitle()));
        articleElement.appendChild(titleElement);

        // Add bodyText element
        Element bodyText = document.createElement("bodyText");
        bodyText.appendChild(document.createTextNode(article.getBodyText()));
        articleElement.appendChild(bodyText);
      }

      // Serialize the document to XML file
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      StreamResult result = new StreamResult(new File(fileName));
      transformer.transform(new DOMSource(document), result);

      System.out.println("[INFO] - XML file created successfully.");
    } catch (Exception e) {
      System.out.println("[ERROR] - Error while serializing articles");
      e.printStackTrace();
    }
  }

  /**
   * Serialize article data to the xml file with the default filename articles.xml
   *
   * @param articlesList the articles to serialize
   */
  public void serialize(Article[] articlesList) {
    serialize(articlesList, "articles.xml");
  }
}
