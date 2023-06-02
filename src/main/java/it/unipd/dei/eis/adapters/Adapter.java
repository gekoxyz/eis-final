package it.unipd.dei.eis.adapters;

import it.unipd.dei.eis.Article;

import java.util.ArrayList;

/**
 * The abstract class {@code Adapter} represents a generic adapter that loads articles from a folder
 * and provides access to them through an ArrayList. New sources should extend this {@code Adapter} class.
 */
abstract class Adapter {
  /**
   * The path of the folder where the articles are stored
   */
  protected String folderPath;
  /**
   * The list of articles loaded by the adapter.
   */
  protected ArrayList<Article> articlesList;

  /**
   * Constructs an {@code Adapter} object with the specified folder path.
   *
   * @param folderPath the path of the folder where the articles are stored
   */
  public Adapter(String folderPath) {
    this.folderPath = folderPath;
    this.articlesList = new ArrayList<>();
  }

  /**
   * Constructs an {@code Adapter} object with the default folder path.
   */
  public Adapter() {
    this.articlesList = new ArrayList<>();
  }

  /**
   * Loads the articles from the specified folder path to the {@code articlesList}
   */
  public abstract void loadArticles();

  /**
   * Returns the articles loaded by the adapter
   *
   * @return an array of {@code Article} objects containing the loaded articles
   */
  public Article[] getArticles() {
    return articlesList.toArray(new Article[0]);
  }
}
