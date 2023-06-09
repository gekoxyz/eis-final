package it.unipd.dei.eis.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import it.unipd.dei.eis.Article;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Adapter for <a href="https://open-platform.theguardian.com/">The Guardian API</a>
 */
public class TheGuardianJsonAdapter extends Adapter {
  /**
   * Constructs a TheGuardianJsonAdapter with the default folder path "./assets/theguardian/".
   * The folder path is specified by the superclass Adapter.
   */
  public TheGuardianJsonAdapter() {
    super("./assets/theguardian/");
  }

  /**
   * Constructs a TheGuardianJsonAdapter with the specified folder path.
   *
   * @param folderPath The folder path where the JSON files are located.
   */
  public TheGuardianJsonAdapter(String folderPath) {
    super(folderPath);
  }

  /**
   * Loads a list of all the article files from the folder, then it calls the loadArticlesFromList
   * method to load the articles from the file list.
   */
  public void loadAllArticles() {
    // Get an array of all files in the folder
    File[] files = new File(folderPath).listFiles();
    // load the actual articles
    loadArticlesFromList(files);
  }

  /**
   * Loads articles from the specified list of files.
   *
   * @param files The array of files containing the articles in JSON format.
   */
  public void loadArticlesFromList(File[] files) {
    // Sorting alphabetically so Winzzoz and Linux/OSX have the same ordering
    Arrays.sort(files, Comparator.comparing(File::getName));
    for (File filePath : files) {
      File jsonFile = new File(filePath.toString());
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        // Going into the json nodes to extract data
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

  /**
   * Calls The Guardian's API and saves the entire json response into the appropriate folder in the assets. The called endpoint is
   * {@code https://content.guardianapis.com/search?api-key=********&page=i&show-fields=bodyText&page-size=200&q=query}
   *
   * @param pages the number of pages to be downloaded
   * @param query the search query to use for the API call
   * @return an array of downloaded files containing the API responses
   */
  public File[] callApi(int pages, String query) {
    ArrayList<File> downloadedFiles = new ArrayList<>();
    // The dotenv loads the private API key to make the call to The Guardian
    Dotenv dotenv = Dotenv.load();
    // The current timestamp is used as the filename
    try {
      for (int pageNumber = 1; pageNumber <= pages; pageNumber++) {
        // setting up request URL with the API key and page number
        URL url = new URL("https://content.guardianapis.com/search?api-key=" + dotenv.get("THEGUARDIAN_API_KEY") +
                "&page=" + pageNumber + "&show-fields=bodyText&page-size=200&q=" + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        // getting the actual response into a String
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
          String line;
          while ((line = reader.readLine()) != null) {
            response.append(line);
          }
        }
        String jsonResponse = response.toString();
        // writing response to file
        String fileName = generateFileName();
        String filePath = folderPath + fileName;
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
          writer.write(jsonResponse);
        }
        System.out.println("[INFO] - API response saved to " + filePath);
        downloadedFiles.add(new File(fileName));
      }
    } catch (IOException e) {
      System.err.println("[INFO] - Error calling the API: " + e.getMessage());
    }
    return downloadedFiles.toArray(new File[0]);
  }

  /**
   * Generates the file name for the articles that the api is going to download.
   *
   * @return the filename formatted as {@code theguardian_articles_currenttimestamp.json}
   */
  private String generateFileName() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
    return "theguardian_articles_" + LocalDateTime.now().format(formatter) + ".json";
  }

  /**
   * Default call to the API which downloads just one page with no specified query
   */
  public void callApi() {
    callApi(1, "");
  }

  /**
   * Call to the API which downloads the specified number of pages with no specified query
   *
   * @param pages the number of pages to download
   */
  public void callApi(int pages) {
    callApi(pages, "");
  }

}
