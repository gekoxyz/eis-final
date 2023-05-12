package it.unipd.dei.eis;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NyTimesCsvWrapper {

  private String filePath;

  public NyTimesCsvWrapper() {
    filePath = null;
  }

  public NyTimesCsvWrapper(String filePath) {
    this.filePath = filePath;
  }

  public void saveArticles() throws CsvValidationException {
    // load artcle file
    CSVReader reader = null;
    ArrayList<Article> articles_list = new ArrayList<Article>();

    try {
      reader = new CSVReader(new FileReader(filePath));
    } catch (FileNotFoundException e) {
      System.out.println("[ERROR] - Check the file name and path");
      throw new RuntimeException(e);
    }

    String[] nextline = null;
    // create ArticleList with ArrayList of articles
    try {
      while ((nextline = reader.readNext()) != null) {
        Article temp_article = new Article(nextline[0], nextline[1], "", nextline[2], nextline[3],
            nextline[4], nextline[5], nextline[6]);
        articles_list.add(temp_article);
      }
    } catch (IOException e) {
      System.out.println("[ERROR] - Error while reading nextline");
    }

    System.out.println("ELEMENTS IN ARTICLES LIST " + articles_list.size());

    // save to file
  }

}
