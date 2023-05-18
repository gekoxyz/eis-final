package it.unipd.dei.eis.adapters;

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
  }

  public Article[] getArticles() {
    return articlesList.toArray(new Article[0]);
  }
}
