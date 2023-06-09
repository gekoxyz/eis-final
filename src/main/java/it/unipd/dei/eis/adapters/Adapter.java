package it.unipd.dei.eis.adapters;

import it.unipd.dei.eis.Article;

import java.io.File;
import java.util.ArrayList;

/**
 * The abstract class {@code Adapter} represents a generic adapter that loads articles from a folder
 * and provides access to them through an ArrayList. New sources should extend this {@code Adapter} class.
 */
abstract class Adapter {

  /**
   * The list of articles loaded by the adapter.
   */
  protected ArrayList<Article> articlesList;

  /**
   * Constructs an {@code Adapter} object with the default folder path.
   */
  public Adapter() {
    this.articlesList = new ArrayList<>();
  }

  /**
   * Loads all the articles from the adapter folder to the {@code articlesList}
   */
  public abstract void loadAllArticles();

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
}
