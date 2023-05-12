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

  public void saveArticles() throws CsvValidationException, IOException {
    // load artcle file
    CSVReader reader = null;
    ArrayList<Article> articles_list = new ArrayList<Article>();

    reader = new CSVReader(new FileReader("./assets/nytimes/nytimes_articles_v2.csv"));
    String[] nextline;
    // create ArticleList with ArrayList of articles
    while ((nextline = reader.readNext()) != null) {
      Article temp_article = new Article(nextline[0], nextline[1], "", nextline[2], nextline[3],
          nextline[4], nextline[5], nextline[6]);
      articles_list.add(temp_article);
    }

    System.out.println("ELEMENTS IN ARTICLES LIST " + articles_list.size());

    // save to file
  }

}
