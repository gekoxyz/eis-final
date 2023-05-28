package it.unipd.dei.eis.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import it.unipd.dei.eis.Article;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TheGuardianJsonAdapter {

  private String filePath = "";

  ArrayList<Article> articlesList = null;

  public TheGuardianJsonAdapter(String filePath) {
    this.filePath = filePath;
    this.articlesList = new ArrayList<Article>();
  }

  public TheGuardianJsonAdapter() {
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

  public void request() {
    Dotenv dotenv = Dotenv.load();
//    String apiUrl = "https://content.guardianapis.com/search?api-key=********&page=1";
    String apiUrl = "https://content.guardianapis.com/search?api-key=" + dotenv.get("THEGUARDIAN_API_KEY") + "&page=1&show-fields=bodyText";
    String outputFile = "./assets/the_guardian/response.json";
    // TODO : IT'S WRITTEN BY MONKEY, FIX
    filePath = outputFile;

    try {
      String jsonResponse = callApi(apiUrl);
      try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {
        writer.write(jsonResponse);
      }
      System.out.println("[INFO] - API response saved to " + outputFile);
    } catch (IOException e) {
      System.err.println("[INFO] - Error calling the API: " + e.getMessage());
    }
  }

  // TODO : JOIN WITH UPPER
  public static String callApi(String apiUrl) throws IOException {
    URL url = new URL(apiUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    StringBuilder response = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
    }
    return response.toString();
  }

  public Article[] getArticles() {
    return articlesList.toArray(new Article[0]);
  }
}
