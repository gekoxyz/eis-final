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

    String text = "President Barack & Obama was born in Hawaii. &amp; He was elected in 2008.";

    // set up pipeline properties
    Properties props = new Properties();
    // set the list of annotators to run
    props.setProperty("annotators", "tokenize");
    props.setProperty("tokenize.options", "normalizeAmpersandEntity=true");
    // build pipeline
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    // create a document object


    for (Article article : articles) {

      CoreDocument doc = new CoreDocument(article.getBodyText());

      // annotate
      pipeline.annotate(doc);
      // display tokens
      for (CoreLabel tok : doc.tokens()) {
        System.out.printf((tok.word()) + "%n");
      }
    }

  }
}