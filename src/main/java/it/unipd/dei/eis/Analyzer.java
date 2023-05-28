package it.unipd.dei.eis;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;

import java.io.*;
import java.util.*;

public class Analyzer {
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    String text = "President Barack Obama was born in Hawaii.  He was elected in 2008.";
    // set up pipeline properties
    Properties props = new Properties();
    // set the list of annotators to run
    props.setProperty("annotators", "tokenize");
    // build pipeline
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    // create a document object
    CoreDocument document = pipeline.processToCoreDocument(text);
    List<CoreLabel> tokens = document.tokens();

    for (CoreLabel token :
            tokens) {
      System.out.println(token);
    }

//    System.out.println("STANFORD CORENLP");
  }
}