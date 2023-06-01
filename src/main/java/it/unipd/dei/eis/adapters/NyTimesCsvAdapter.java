package it.unipd.dei.eis.adapters;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.unipd.dei.eis.Article;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NyTimesCsvAdapter {

  private String folderPath = "./assets/nytimes/";

  ArrayList<Article> articlesList = new ArrayList<>();

  public NyTimesCsvAdapter(String folderPath) {
    this.folderPath = folderPath;
  }

  public NyTimesCsvAdapter() {
  }

  public void loadArticles() {
    CSVReader reader;
    String[] nextline;

    File[] files = new File(folderPath).listFiles(); // Get an array of all files in the folder
    assert files != null;
    for (File filePath : files) {
      // Open CSV with CSVReader
      try {
        reader = new CSVReader(new FileReader(filePath.toString()));
      } catch (FileNotFoundException e) {
        System.out.println("[ERROR] - Check the file name and path");
        throw new RuntimeException(e);
      }
      // Parse CSV and create articles
      try {
        // this skips the first line because it defines the fields of the csv
        reader.readNext();
        while ((nextline = reader.readNext()) != null) {
          // checking that the csv has the correct number of fields
          if (nextline.length != 7) {
            throw new CsvValidationException(
                    "[ERROR] - The file doesn't have the correct number of fields");
          }
          String title = nextline[2];
          String bodyText = nextline[3];
          articlesList.add(new Article(title, bodyText));
        }
        reader.close();
      } catch (IOException e) {
        System.out.println("[ERROR] - Error while reading nextline");
      } catch (CsvValidationException e) {
        System.out.println("[ERROR] - Couldn't validate the CSV");
      }
    }
  }

  public Article[] getArticles() {
    return articlesList.toArray(new Article[0]);
  }
}
