package it.unipd.dei.eis;

import com.opencsv.exceptions.CsvValidationException;
import it.unipd.dei.eis.adapters.NyTimesCsvAdapter;
import it.unipd.dei.eis.serialization.Deserializer;
import it.unipd.dei.eis.serialization.Serializer;

public class Main {

  public static void main(String[] args) {
    Serializer serializer = new Serializer();

    NyTimesCsvAdapter nyTimesCsvAdapter = new NyTimesCsvAdapter(
            "./assets/nytimes/nytimes_articles_v2.csv");
    try {
      nyTimesCsvAdapter.loadArticles();
      serializer.serialize(nyTimesCsvAdapter.getArticles());
    } catch (CsvValidationException e) {
      throw new RuntimeException(e);
    }

//    NyTimesCsvAdapter nyTimesCsvAdapter2 = new NyTimesCsvAdapter("./assets/nytimes/temp.csv");
//    try {
//      nyTimesCsvAdapter2.loadArticles();
//      serializer.serialize(nyTimesCsvAdapter2.getArticles());
//    } catch (CsvValidationException e) {
//      throw new RuntimeException(e);
//    }

    Deserializer deserializer = new Deserializer();

    Article[] articles = deserializer.deserialize("articles.xml");
    System.out.println(articles.length + " articles in the XML file");
  }
}