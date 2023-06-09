package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.NyTimesCsvAdapter;
import it.unipd.dei.eis.adapters.TheGuardianJsonAdapter;
import it.unipd.dei.eis.serialization.Deserializer;
import it.unipd.dei.eis.serialization.Serializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Main for the Article Downloader
 */


/**
 * Usage:
 * <p>
 * To Download articles use the following syntax:
 * java eis-final -d [source]</p>
 * <p>
 * To Download and Analyze articles use the following syntax:
 * java eis-final -da [source]
 * </p>
 * <p>
 * To Analyze articles use the following syntax:
 * java eis-final -a
 * </p>
 *
 * <p>Sources:
 * -nytimes
 * -theguardianapi
 * -theguardianlocal
 * </p>
 *
 *
 *
 * <p>
 * To add a source to the program:
 * -Create a custom adapter in the "src/main/java/it.unipd.dei.eis/adapters folder and extend the Adapter class"
 * -Add a case to the switch case in the main with the custom source name (e.g. case "new-source": [adapter-code];break;)
 * </p>
 */
public class Main {

  public static void main(String[] args) {
//    InteractiveMenu interactiveMenu = new InteractiveMenu();
//    interactiveMenu.runMenu();

// TODO: Don't delete, check nuclear = 1000 in nyTimes

    NyTimesCsvAdapter nyTimesCsvAdapter = new NyTimesCsvAdapter();
    nyTimesCsvAdapter.loadAllArticles();
    Serializer serializer = new Serializer();
    serializer.serialize(nyTimesCsvAdapter.getArticles());
    Analyzer analyzer = new Analyzer();
    analyzer.analyze();
  }

//  public static void main(String[] args) throws IOException {
//    Serializer serializer = new Serializer();
//    Deserializer deserializer = new Deserializer();
//    Article[] articles;
//
//    if (args[0].equals("-d") || args[0].equals("-da")) {
//      switch(args[1]){
//        case "nytimes":
//          NyTimesCsvAdapter nyTimesCsvAdapter = new NyTimesCsvAdapter();
//          nyTimesCsvAdapter.loadArticles();
//          serializer.serialize(nyTimesCsvAdapter.getArticles(), "articles.xml");
//
//          articles = deserializer.deserialize("articles.xml");
//          System.out.println(articles.length + " articles in the XML file");
//          if (args[0].equals("-da")) {
//            Analyzer.main(null);
//          }
//          break;
//
//        case "theguardianlocal":
//          TheGuardianJsonAdapter theGuardianJsonAdapter1 = new TheGuardianJsonAdapter();
//          theGuardianJsonAdapter1.loadArticles();
//          serializer.serialize(theGuardianJsonAdapter1.getArticles(), "articles.xml");
//
//          articles = deserializer.deserialize("articles.xml");
//          System.out.println(articles.length + " articles in the XML file");
//          if (args[0].equals("-da")) {
//            Analyzer.main(null);
//          }
//          break;
//        case "theguardianapi":
//          TheGuardianJsonAdapter theGuardianJsonAdapter2 = new TheGuardianJsonAdapter();
//          theGuardianJsonAdapter2.callApi();
//          serializer.serialize(theGuardianJsonAdapter2.getArticles(), "articles.xml");
//
//          articles = deserializer.deserialize("articles.xml");
//          System.out.println(articles.length + " articles in the XML file");
//          if (args[0].equals("-da")) {
//            Analyzer.main(null);
//          }
//          break;
//      }
//    }
//    else
//    {
//      Analyzer.main(null);
//    }
//  }
}