package it.unipd.dei.eis.adapters;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.unipd.dei.eis.Article;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Adapter for the NyTimes csv file that has been provided us through moodle
 */
public class NyTimesCsvAdapter extends Adapter {

  /**
   * folder path as specified by the superclass {@code Adapter}
   */
  private String folderPath = "./assets/nytimes/";

  /**
   * Loads articles from the specified folder as specified by the superclass {@code Adapter}
   */
  public void loadArticles() {
    // Get an array of all files in the folder
    File[] files = new File(folderPath).listFiles();
    assert files != null;
    for (File filePath : files) {
      // Open CSV with CSVReader
      CSVReader reader;
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
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
          // checking that the csv has the correct number of fields
          if (nextLine.length != 7) {
            throw new CsvValidationException(
                    "[ERROR] - The file doesn't have the correct number of fields");
          }
          String title = nextLine[2];
          String bodyText = nextLine[3];
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

}
