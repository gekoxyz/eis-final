package it.unipd.dei.eis;

import java.util.ArrayList;

/**
 * The ArticleList class contains an ArrayList of articles (could be changed in the future with a
 * more efficient data structure)
 */
public class ArticleList {

  private ArrayList<Article> articleArrayList;

  public ArticleList() {
    articleArrayList = new ArrayList<Article>();
  }

  public ArticleList(ArrayList<Article> articleArrayList) {
    this.articleArrayList = articleArrayList;
  }

}
