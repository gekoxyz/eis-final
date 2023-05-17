package it.unipd.dei.eis;

/**
 * An Article class contains all the most important information of an article
 */
public class Article {

  private String title;
  private String bodyText;

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
