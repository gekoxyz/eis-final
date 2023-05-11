package it.unipd.dei.eis;

public class Article {

  private String id;
  private String webUrl;
  private String apiUrl;
  private String title;
  private String bodyText;
  private String publicationDate;
  private String publisher;
  private String source;

  public Article() {
  }

  public Article(String id, String webUrl, String apiUrl, String title, String bodyText,
      String publicationDate, String publisher, String source) {
    this.id = id;
    this.webUrl = webUrl;
    this.apiUrl = apiUrl;
    this.title = title;
    this.bodyText = bodyText;
    this.publicationDate = publicationDate;
    this.publisher = publisher;
    this.source = source;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getWebUrl() {
    return webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

  public String getApiUrl() {
    return apiUrl;
  }

  public void setApiUrl(String apiUrl) {
    this.apiUrl = apiUrl;
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

  public String getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(String publicationDate) {
    this.publicationDate = publicationDate;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  @Override
  public String toString() {
    return "Article{" + "id='" + id + '\'' + ", webUrl='" + webUrl + '\'' + ", apiUrl='" + apiUrl
        + '\'' + ", title='" + title + '\'' + ", bodyText='" + bodyText + '\''
        + ", publicationDate='" + publicationDate + '\'' + ", publisher='" + publisher + '\''
        + ", source='" + source + '\'' + '}';
  }
}
