package it.unipd.dei.eis;

/**
 * Class that contains the most essential information about an article
 */
public class Article {

  /**
   * Title of the article
   */
  private String title = "";
  /**
   * Body of the article
   */
  private String bodyText = "";

  /**
   * Creates a basic Article
   *
   * @param title    Title of the article
   * @param bodyText Body of the article
   */
  public Article(String title, String bodyText) {
    this.title = title;
    this.bodyText = bodyText;
  }

  /**
   * Empty constructor, needed by the serializer
   */
  public Article() {
  }

  /**
   * Retrieves the title of the article.
   *
   * @return The title of the article as a string.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the article.
   *
   * @param title The title of the article as a string.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Retrieves the body text of the article.
   *
   * @return The body text of the article as a string.
   */
  public String getBodyText() {
    return bodyText;
  }

  /**
   * Sets the body text of the article.
   *
   * @param bodyText The body text of the article as a string.
   */
  public void setBodyText(String bodyText) {
    this.bodyText = bodyText;
  }

  /**
   * Returns a string representation of the article.
   *
   * @return A string representation of the article.
   */
  @Override
  public String toString() {
    return "<article>\n" +
            "\t<title>" + title + "</title>\n" +
            "\t<bodyText>" + bodyText + "</bodyText>\n" +
            "</article>";
  }
}
