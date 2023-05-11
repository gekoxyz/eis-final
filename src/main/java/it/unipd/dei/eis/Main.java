package it.unipd.dei.eis;

import com.opencsv.CSVReader;

import com.opencsv.exceptions.CsvValidationException;
import java.io.*;

import java.util.ArrayList;

public class Main {

  public static void main(String[] args) {

    CSVReader reader = null;
    ArrayList<Article> articles_list = new ArrayList<Article>();

    // BAD CODE!!! JUST FOR TESTING
    try {
      reader = new CSVReader(new FileReader("./assets/nytimes/nytimes_articles_v2.csv"));
      String[] nextline;
      while ((nextline = reader.readNext()) != null) {
        Article temp_article = new Article(nextline[0], nextline[1], "", nextline[2], nextline[3],
            nextline[4], nextline[5], nextline[6]);
        articles_list.add(temp_article);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (CsvValidationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    System.out.println("ELEMENTS IN ARTICLES LIST " + articles_list.size());

  }
}
