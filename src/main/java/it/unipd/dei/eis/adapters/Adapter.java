package it.unipd.dei.eis.adapters;

import it.unipd.dei.eis.Article;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The abstract class {@code Adapter} represents a generic adapter that loads articles from a folder
 * and provides access to them through an ArrayList. New sources should extend this {@code Adapter} class.
 */
public abstract class Adapter {

  /**
   * The list of articles loaded by the adapter.
   */
  protected ArrayList<Article> articlesList;
  protected String folderPath = "./assets/";
  protected String folderName;

  /**
   * Constructs an {@code Adapter} object with the default folder path.
   */
  public Adapter() {
    this.articlesList = new ArrayList<>();
    this.folderName = getFolderName();
  }

  public Adapter(String folderPath) {
    this.articlesList = new ArrayList<>();
    this.folderPath = folderPath;
    this.folderName = getFolderName();
  }

  public void loadAllArticles() {
    // Get an array of all files in the folder
    File[] files = new File(folderPath).listFiles();
    // load the actual articles
    loadArticlesFromList(files);
  }

  public void loadArticleFromFileName(File file) {
    loadArticlesFromList(new File[]{file});
  }


  /**
   * Loads a list of articles from the adapter folder to the articlesList
   *
   * @param files
   */
  public abstract void loadArticlesFromList(File[] files);

  /**
   * Returns the articles loaded by the adapter
   *
   * @return an array of {@code Article} objects containing the loaded articles
   */
  public Article[] getArticles() {
    return articlesList.toArray(new Article[0]);
  }

  public String getFolderName() {

    Pattern regexPattern = Pattern.compile("[^/]+/$");
    Matcher matcher = regexPattern.matcher(folderPath);
    String lastPart = "";
    if (matcher.find()) {
      lastPart = matcher.group(0);
      // Remove the trailing slash
      lastPart = lastPart.substring(0, lastPart.length() - 1).replace(".java", "");
    } else {
      System.out.println("[ERROR] - bad filepath");
    }
    return lastPart;
  }
}
