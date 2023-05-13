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

  // It has one more tab so the serializer produces a good article
  public String toSerializableString() {
    return "\t<article>\n" +
        "\t\t<title>" + title + "</title>\n" +
        "\t\t<bodyText>" + bodyText + "</bodyText>\n" +
        "\t</article>";
  }

  @Override
  public String toString() {
    return "<article>\n" +
        "\t<title>" + title + "</title>\n" +
        "\t<bodyText>" + bodyText + "</bodyText>\n" +
        "</article>";
  }
}
