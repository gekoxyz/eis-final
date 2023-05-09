package it.unipd.dei.eis;

import com.opencsv.CSVReader;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

public class Main {

  public static void main(String[] args) throws IOException, CsvException {

    CSVReader reader = null;
    try {
      //parsing a CSV file into CSVReader class constructor
      reader = new CSVReader(new FileReader("./assets/nytimes_articles_v1.csv"));

      String[] nextLine;
      //reads one line at a time
      while ((nextLine = reader.readNext()) != null) {
        for (String token : nextLine) {
          System.out.println(token);
        }
        System.out.print("\n");
      }
//            Article a = new Article()
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
