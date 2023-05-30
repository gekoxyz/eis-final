package it.unipd.dei.eis;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import it.unipd.dei.eis.serialization.Deserializer;

import java.io.*;
import java.util.*;

public class Analyzer {

  public static void main(String[] args) {

    Deserializer deserializer = new Deserializer();
    Article[] articles = deserializer.deserialize("articles.xml");

    // set up pipeline properties
    Properties props = new Properties();
    // set the list of annotators to run
    props.setProperty("annotators", "tokenize");
    props.setProperty("tokenize.options", "normalizeAmpersandEntity=true");
    // build pipeline
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    // create a document object

    HashSet<String> uniqueWords = new HashSet<>();
    HashMap<String, Integer> wordCounter = new HashMap<>();

    for (Article article : articles) {
      // annotate the body text
      CoreDocument doc = new CoreDocument(article.getBodyText());
      pipeline.annotate(doc);

      for (CoreLabel tok : doc.tokens()) {
        // TODO : ADD IF NOT IN STOPLIST
        uniqueWords.add(tok.word());
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
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        return o2.getValue().compareTo(o1.getValue());
      }
    });

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
}