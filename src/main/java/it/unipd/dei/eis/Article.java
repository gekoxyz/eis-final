package it.unipd.dei.eis;

/**
 * Class that contains the most essential information about an article
 */
public class Article {

  /**
   * Title of the article
   */
  private String title;
  /**
   * Body of the article
   */
  private String bodyText;

  /**
   * Creates a basic Article
   *
   * @param title Title of the article
   * @param bodyText Body of the article
   */
  public Article(String title, String bodyText) {
    this.title = title;
    this.bodyText = bodyText;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBodyText() {
    return bodyText;
  }

  public void setBodyText(String bodyText) {
    this.bodyText = bodyText;
  }

  @Override
  public String toString() {
    return "<article>\n" +
            "\t<title>" + title + "</title>\n" +
            "\t<bodyText>" + bodyText + "</bodyText>\n" +
            "</article>";
  }
}
