package it.unipd.dei.eis;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {

//      reader = new CSVReader(new FileReader("./assets/nytimes/nytimes_articles_v2.csv"));
    NyTimesCsvWrapper nyTimesCsvWrapper = new NyTimesCsvWrapper(
        "./assets/nytimes/nytimes_articles_v2.csv");
    try {
      nyTimesCsvWrapper.saveArticles();
    } catch (CsvValidationException e) {
      throw new RuntimeException(e);
    }
  }
}
