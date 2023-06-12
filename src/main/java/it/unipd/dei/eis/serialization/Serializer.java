package it.unipd.dei.eis.serialization;

import it.unipd.dei.eis.Article;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * The Serializer class provides methods to serialize Article objects into an XML file.
 */
public class Serializer {

  /**
   * Serializes an array of Article objects into an XML file.
   *
   * @param articlesList The array of Article objects to be serialized.
   * @param fileName     The name of the XML file to serialize the articles into.
   */
  public void serialize(Article[] articlesList, String fileName) {
    try {
      File xmlFile = new File(fileName);
      Document document;
      DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

      if (!xmlFile.exists()) {
        // Create a new XML file if it doesn't exist
        document = documentBuilder.newDocument();
        document.appendChild(document.createElement("articles"));
        writeXmlToFile(xmlFile, document);
        System.out.println("[INFO] - XML file created successfully.");
      } else {
        // Load the existing XML file
        document = documentBuilder.parse(xmlFile);
      }

      Element rootElement = document.getDocumentElement();

      for (Article article : articlesList) {
        Element articleElement = document.createElement("article");
        rootElement.appendChild(articleElement);

        // Add title
        Element titleElement = document.createElement("title");
        titleElement.setTextContent(article.getTitle());

        articleElement.appendChild(titleElement);

        // Add bodyText
        Element bodyTextElement = document.createElement("bodyText");
        bodyTextElement.setTextContent(article.getBodyText());
        articleElement.appendChild(bodyTextElement);
      }

      // Write the modified XML document back to the file
      writeXmlToFile(xmlFile, document);
      System.out.println("[INFO] - Added " + articlesList.length + " articles to the XML file");
    } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
      System.out.println("[ERROR] - Error while serializing articles");
      e.printStackTrace();
    }
  }

  /**
   * Writes the XML document to a file.
   *
   * @param xmlFile  The file to write the XML document to.
   * @param document The XML document to be written.
   */
  private void writeXmlToFile(File xmlFile, Document document) {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.transform(new DOMSource(document), new StreamResult(xmlFile));
    } catch (TransformerException e) {
      e.printStackTrace();
    }
  }

  /**
   * Serializes an array of Article objects into a default XML file named "articles.xml" in the "./assets" directory.
   *
   * @param articlesList The array of Article objects to be serialized.
   */
  public void serialize(Article[] articlesList) {
    serialize(articlesList, "./assets/articles.xml");
  }
}
