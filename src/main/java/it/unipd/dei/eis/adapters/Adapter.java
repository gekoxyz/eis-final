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
   * Initializes the articlesList and sets the folderName.
   */
  public Adapter() {
    this.articlesList = new ArrayList<>();
    this.folderName = getFolderName();
  }

  /**
   * Constructs an Adapter object with the specified folder path.
   * Initializes the articlesList with an empty ArrayList, and sets the folderPath and folderName.
   *
   * @param folderPath The path to the folder containing the articles.
   */
  public Adapter(String folderPath) {
    this.articlesList = new ArrayList<>();
    this.folderPath = folderPath;
    this.folderName = getFolderName();
  }

  /**
   * Loads all articles from the adapter's folder.
   * It gets an array of all files in the folder and calls the loadArticlesFromList method to load the articles.
   */
  public void loadAllArticles() {
    // Get an array of all files in the folder
    File[] files = new File(folderPath).listFiles();
    // load the actual articles
    loadArticlesFromList(files);
  }

  /**
   * Loads an article from a specific file.
   *
   * @param file The file containing the article to be loaded.
   */
  public void loadArticleFromFileName(File file) {
    loadArticlesFromList(new File[]{file});
  }

  /**
   * Loads a list of articles from the adapter folder to the articlesList
   *
   * @param files The array of files containing the articles.
   */
  public abstract void loadArticlesFromList(File[] files);

  /**
   * Returns the articles loaded by the adapter.
   *
   * @return An array of {@code Article} objects containing the loaded articles.
   */
  public Article[] getArticles() {
    return articlesList.toArray(new Article[0]);
  }

  /**
   * Extracts the folder name from the folder path.
   *
   * @return The folder name.
   */
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
