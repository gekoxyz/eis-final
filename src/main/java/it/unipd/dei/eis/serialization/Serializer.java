package it.unipd.dei.eis.serialization;

import it.unipd.dei.eis.Article;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
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
      FileOutputStream fileOutputStream = new FileOutputStream(fileName);
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
      XMLEncoder xmlEncoder = new XMLEncoder(bufferedOutputStream);
      xmlEncoder.writeObject(articlesList);
      xmlEncoder.close();
      bufferedOutputStream.close();
      fileOutputStream.close();
      System.out.println("[INFO] - Serialized" + articlesList.length + " articles to the XML file");
    } catch (IOException e) {
      System.out.println("[ERROR] - Error while serializing the articles");
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
