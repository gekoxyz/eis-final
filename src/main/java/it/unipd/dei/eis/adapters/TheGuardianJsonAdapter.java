package it.unipd.dei.eis.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.eis.Article;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
//          String type = resultNode.get("bodyText").asText();
          String bodyText = resultNode.get("fields").get("bodyText").asText();
          articlesList.add(new Article(webTitle, bodyText));
        }
      } else {
        System.out.println("Results field not found or not an array");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // TODO : http request to the API
  public Article[] getArticles() {
    return articlesList.toArray(new Article[0]);
  }
}


/*
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonFieldExtractionExample {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File("path/to/your/json/file.json");
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            // Extract a specific field
            JsonNode nameNode = rootNode.get("name");
            if (nameNode != null && nameNode.isTextual()) {
                String name = nameNode.asText();
                System.out.println("Name: " + name);
            } else {
                System.out.println("Name field not found or not a string");
            }

            // Extract another field
            JsonNode ageNode = rootNode.get("age");
            if (ageNode != null && ageNode.isInt()) {
                int age = ageNode.asInt();
                System.out.println("Age: " + age);
            } else {
                System.out.println("Age field not found or not an integer");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


 */