package it.unipd.dei.eis.serialization;

import it.unipd.dei.eis.Article;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;


public class Serializer {

  public Serializer() {
  }

  public void serialize(Article[] articlesList) {

    File file = new File("articles.xml");

    try (PrintWriter writer = new PrintWriter(file)) {
      writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<articles>\n");
      for (Article article : articlesList) {
        writer.println(article.toSerializableString());
      }
      writer.write("</articles>");
      System.out.println("[INFO] - Articles serialized successfully");
    } catch (IOException e) {
      System.out.println("[ERROR] - Error while serializing articles");
      e.printStackTrace();
    }
  }
}
