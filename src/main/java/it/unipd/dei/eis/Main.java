package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.TheGuardianJsonAdapter;
import it.unipd.dei.eis.serialization.Serializer;

public class Main {

  public static void main(String[] args) {
    Serializer serializer = new Serializer();

//    NyTimesCsvAdapter nyTimesCsvAdapter = new NyTimesCsvAdapter(
//            "./assets/nytimes/nytimes_articles_v2.csv");
//    try {
//      nyTimesCsvAdapter.loadArticles();
//      serializer.serialize(nyTimesCsvAdapter.getArticles(), "articles.xml");
//    } catch (CsvValidationException e) {
//      throw new RuntimeException(e);
//    }

//    NyTimesCsvAdapter nyTimesCsvAdapter2 = new NyTimesCsvAdapter("./assets/nytimes/temp.csv");
//    try {
//      nyTimesCsvAdapter2.loadArticles();
//      serializer.serialize(nyTimesCsvAdapter2.getArticles());
//    } catch (CsvValidationException e) {
//      throw new RuntimeException(e);
//    }

//    Deserializer deserializer = new Deserializer();

//    Article[] articles = deserializer.deserialize("articles.xml");
//    System.out.println(articles.length + " articles in the XML file");

//    TheGuardianJsonAdapter theGuardianJsonAdapter = new TheGuardianJsonAdapter("./assets/the_guardian/theguardian_articles_v1_p01.json");
//    theGuardianJsonAdapter.loadArticles();

//    serializer.serialize(theGuardianJsonAdapter.getArticles(), "articles.xml");

    TheGuardianJsonAdapter theGuardianJsonAdapter1 = new TheGuardianJsonAdapter();
    theGuardianJsonAdapter1.callApi();
    theGuardianJsonAdapter1.loadArticles();
    serializer.serialize(theGuardianJsonAdapter1.getArticles(), "articles.xml");
  }
}