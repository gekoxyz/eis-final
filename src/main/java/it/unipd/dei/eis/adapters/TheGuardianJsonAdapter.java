package it.unipd.dei.eis.adapters;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.eis.Article;
import com.fasterxml.jackson.databind.JsonNode;


import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

public class TheGuardianJsonAdapter {

  private String filePath = "";

  ArrayList<Article> articlesList = null;

  public TheGuardianJsonAdapter(String filePath) {
    this.filePath = filePath;
    this.articlesList = new ArrayList<Article>();
  }

  public void loadArticles() {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      File jsonFile = new File(filePath);
      JsonNode rootNode = objectMapper.readTree(jsonFile);

      JsonNode resultsNode = rootNode.get("response").get("results");
      if (resultsNode != null && resultsNode.isArray()) {
        for (JsonNode resultNode : resultsNode) {
          String webTitle = resultNode.get("webTitle").asText();
          String bodyText = resultNode.get("fields").get("bodyText").asText();
          articlesList.add(new Article(webTitle, bodyText));
        }
      } else {
        System.out.println("[WARNING] - Results field not found or not an array");
      }
    } catch (IOException e) {
      System.out.println("[ERROR] - Check the file name and path");
      e.printStackTrace();
    }
  }

  // TODO : http request to the API
  public void request() {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://content.guardianapis.com/search?q=&format=json&api-key=***************"))
            .header("theguardian-Host", "content.guardianapis.com")
            .header("theguardian-Key", "api-key-here")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
    HttpResponse<String> response = null;

    try {
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      //code to elaborate response
      /*
      * from the response take response["response"]["results"] and save only the "id" of every item in articlesId
      *
      *get the full article for every id
      *
      * save every article in articlesList
      * */


    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(response.body());
    //https://content.guardianapis.com/search?q=joe%20biden&api-key=****************&page=1
  }

  public Article[] getArticles() {
    return articlesList.toArray(new Article[0]);
  }
}
