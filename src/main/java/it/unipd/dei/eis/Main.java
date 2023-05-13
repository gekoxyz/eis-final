package it.unipd.dei.eis;

import com.opencsv.exceptions.CsvValidationException;
import it.unipd.dei.eis.adapters.NyTimesCsvAdapter;
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
  }
}
