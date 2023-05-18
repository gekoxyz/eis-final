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

  public Serializer() {
  }

  public void serialize(Article[] articlesList, String fileName) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();

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
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      File xmlFile = new File(fileName);
      StreamResult result = new StreamResult(xmlFile);
      DOMSource source = new DOMSource(document);
      transformer.transform(source, result);

      System.out.println("[INFO] - XML file created successfully.");
    } catch (Exception e) {
      System.out.println("[ERROR] - Error while serializing articles");
      e.printStackTrace();
    }
  }

}
