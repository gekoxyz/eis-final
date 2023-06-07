package it.unipd.dei.eis;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import it.unipd.dei.eis.serialization.Deserializer;

import java.io.*;
import java.util.*;

/**
 * The Analyzer class contains the functions that analyze terms in articles.
 * The Analysys's result is saved in an "output.txt" file*/
public class Analyzer {
  /**
   * Pipeline from the CoreNLP library used to scan terms in Articles
   */
  private StanfordCoreNLP pipeline;

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
   * Main analysis function
   */
  public void analyze() {
    Deserializer deserializer = new Deserializer();
    Article[] articles = deserializer.deserialize("articles.xml");

    HashSet<String> uniqueWords = new HashSet<>();
    HashMap<String, Integer> wordCounter = new HashMap<>();

    // TODO: STOPLIST DOESN'T FILTER ALL. ALTER THE STOPLIST OR USE SOMETHING FROM CORENLP TO AVOID THIS
    // TODO: se pareggio in termine di peso si da preferenza in base all'ordine alfabetico

    HashSet<String> stopList = loadStopList();

    for (Article article : articles) {
      // annotate the body text
      CoreDocument doc = new CoreDocument(article.getBodyText());
      pipeline.annotate(doc);

      for (CoreLabel tok : doc.tokens()) {
        if (!stopList.contains(tok.word())) {
          uniqueWords.add(tok.word());
        }
      }

      for (String el : uniqueWords) {
        wordCounter.put(el, wordCounter.getOrDefault(el, 0) + 1);
      }
      uniqueWords.clear();
    }

    // Step 1: Get the entry set from the map
    Set<Map.Entry<String, Integer>> entrySet = wordCounter.entrySet();

    // Step 2: Convert the entry set to a list
    List<Map.Entry<String, Integer>> list = new ArrayList<>(entrySet);

    // Step 3: Sort the list in descending order
    Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

//    // Step 4: Iterate over the sorted list and print the key-value pairs
//    for (Map.Entry<String, Integer> entry : list) {
//      System.out.println(entry.getKey() + ": " + entry.getValue());
//    }

    String filePath = "output.txt";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      for (int i = 0; i < 50; i++) {
        Map.Entry<String, Integer> entry = list.get(i);
        String key = entry.getKey();
        Integer value = entry.getValue();
        writer.write(key + ": " + value);
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Function that loads the StopList
   * @return the loaded StopList
   */
  private static HashSet<String> loadStopList() {
    HashSet<String> stringList = new HashSet<>();
    try (BufferedReader reader = new BufferedReader(
            new FileReader("./assets/coreNLP/stoplist.txt"))) {
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