package it.unipd.dei.eis.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import it.unipd.dei.eis.Article;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TheGuardianJsonAdapter {

  private String folderPath = "./assets/the_guardian/";

  ArrayList<Article> articlesList;

  public TheGuardianJsonAdapter(String folderPath) {
    this.folderPath = folderPath;
    this.articlesList = new ArrayList<>();
  }

  public TheGuardianJsonAdapter() {
    this.articlesList = new ArrayList<>();
  }

  public void loadArticles() {
    ObjectMapper objectMapper = new ObjectMapper();
    File[] files = new File(folderPath).listFiles(); // Get an array of all files in the folder
    assert files != null;
    for (File filePath : files) {
      File jsonFile = new File(filePath.toString());
      try {

        JsonNode rootNode = objectMapper.readTree(jsonFile);
        JsonNode resultsNode = rootNode.get("response").get("results");
        if (resultsNode != null && resultsNode.isArray()) {
          for (JsonNode resultNode : resultsNode) {
            String webTitle = resultNode.get("webTitle").asText();
            // the guardian sends the & char already escaped. this is already done by xml library
            // it would be better if the javax.xml had a way to avoid escaping the & char  but i think this is pretty painless
            String bodyText = resultNode.get("fields").get("bodyText").asText().replace("&amp;", "&");
            articlesList.add(new Article(webTitle, bodyText));
          }
        } else {
          System.out.println("[WARNING] - Results field not found or not an array");
        }
        System.out.println("[INFO] - Scanned file: " + filePath);

      } catch (IOException e) {
        System.out.println("[ERROR] - Check the file name and path");
        e.printStackTrace();
      }
    }
    System.out.println("[INFO] - Loaded " + articlesList.size() + " articles");
  }

  //    String apiUrl = "https://content.guardianapis.com/search?api-key=********&page=1";
  public void callApi(int pages) {
    Dotenv dotenv = Dotenv.load();
    Instant currentTimestamp = Instant.now();
    String filePath = folderPath + currentTimestamp + ".json";
    try {
      for (int pageNumber = 1; pageNumber <= pages; pageNumber++) {
        // setting up request URL
        URL url = new URL("https://content.guardianapis.com/search?api-key=" + dotenv.get("THEGUARDIAN_API_KEY") + "&page=" + pageNumber + "&show-fields=bodyText&page-size=50");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        // crafting response
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
          String line;
          while ((line = reader.readLine()) != null) {
            response.append(line);
          }
        }
        String jsonResponse = response.toString();
        // writing response
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
          writer.write(jsonResponse);
        }
        System.out.println("[INFO] - API response saved to " + filePath);
      }
    } catch (IOException e) {
      System.err.println("[INFO] - Error calling the API: " + e.getMessage());
    }
  }

  // default call downloads 1 page
  public void callApi() {
    callApi(1);
  }

  public Article[] getArticles() {
    return articlesList.toArray(new Article[0]);
  }
}
