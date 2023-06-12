package it.unipd.dei.eis.adapters;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.unipd.dei.eis.Article;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Adapter for the NyTimes csv file that has been provided through moodle
 */
public class NyTimesCsvAdapter extends Adapter {

  /**
   * Constructs a NyTimesCsvAdapter with the default folder path "./assets/nytimes/".
   * The folder path is specified by the superclass Adapter.
   */
  public NyTimesCsvAdapter() {
    super("./assets/nytimes/");
  }

  /**
   * Constructs a NyTimesCsvAdapter with the specified folder path.
   *
   * @param folderPath The folder path where the CSV files are located.
   */
  public NyTimesCsvAdapter(String folderPath) {
    super(folderPath);
  }

  /**
   * Loads articles from the specified list of files.
   * It reads each file, parses the CSV data, and creates Article objects from the data.
   *
   * @param files The array of files containing the articles.
   */
  public void loadArticlesFromList(File[] files) {
    // Sorting alphabetically so Winzzoz and Linux/OSX have the same ordering
    Arrays.sort(files, Comparator.comparing(File::getName));
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
      System.out.println("[INFO] - Scanned file " + filePath);
    }
  }

}
