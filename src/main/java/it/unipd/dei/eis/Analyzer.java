package it.unipd.dei.eis;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import it.unipd.dei.eis.serialization.Deserializer;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * The Analyzer class contains the functions that analyze terms in articles.
 * The Analysys's result is saved in an "output.txt" file
 */
public class Analyzer {
  /**
   * Pipeline from the CoreNLP library used to scan terms in Articles
   */
  private static StanfordCoreNLP pipeline;
  private static final HashSet<String> stopList = loadStopList();

  /**
   * Initializes the analyzer CoreNLP pipeline
   */
  public Analyzer() {
    // set up pipeline properties
    Properties props = new Properties();
    // set the list of annotators to run
    props.setProperty("annotators", "tokenize");
    props.setProperty("tokenize.options", "normalizeAmpersandEntity=true");
    // build pipeline
    pipeline = new StanfordCoreNLP(props);
  }

  /**
   * Function to analyze the articles
   */
  public void analyze() {
    analyze("articles.xml", "output.xml");
  }

  /**
   * Function to analyze the articles
   *
   * @param inputFile
   * @param outputFile
   */
  public void analyze(String inputFile, String outputFile) {
    Deserializer deserializer = new Deserializer();
    Article[] articles = deserializer.deserialize(inputFile);

    HashSet<String> uniqueWords = new HashSet<>();
    HashMap<String, Integer> wordCounter = new HashMap<>();

    for (Article article : articles) {
      uniqueWords.addAll(tokenizeAndCheck(new CoreDocument(article.getTitle())));
      uniqueWords.addAll(tokenizeAndCheck(new CoreDocument(article.getBodyText())));
      for (String el : uniqueWords) {
        wordCounter.put(el, wordCounter.getOrDefault(el, 0) + 1);
      }
      uniqueWords.clear();
    }

    // Get the entry set from the map and convert it to a list
    List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCounter.entrySet());

    // Sort the list in descending order
    list.sort((entry1, entry2) -> {
      int valueComparison = entry2.getValue().compareTo(entry1.getValue());
      if (valueComparison != 0) {
        return valueComparison; // Sort by value primarily
      }
      return entry1.getKey().compareTo(entry2.getKey()); // Sort by key secondarily
    });

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
      for (int i = 0; i < 50; i++) {
        Map.Entry<String, Integer> entry = list.get(i);
        String key = entry.getKey();
        Integer value = entry.getValue();
        writer.write(key + "=" + value);
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Function to tokenize a {@code CoreDocument} and check that it contains a char or number, to filter out everything else
   *
   * @param document the document to check
   * @return a Set containing the words found in the document
   */
  private static HashSet<String> tokenizeAndCheck(CoreDocument document) {
    Pattern pattern = Pattern.compile(".*[a-zA-Z].*|.*\\d.*");
    HashSet<String> uniqueWords = new HashSet<>();
    pipeline.annotate(document);
    for (CoreLabel tok : document.tokens()) {
      if (!stopList.contains(tok.word()) && pattern.matcher(tok.word()).matches()) {
        uniqueWords.add(tok.word().toLowerCase());
      }
    }
    return uniqueWords;
  }

  /**
   * Function that loads the StopList
   *
   * @return the loaded StopList
   */
  private static HashSet<String> loadStopList() {
    HashSet<String> stringList = new HashSet<>();
    try (BufferedReader reader = new BufferedReader(
            new FileReader("./resources/coreNLP/stoplist.txt"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        stringList.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringList;
  }

}