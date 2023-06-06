package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.NyTimesCsvAdapter;
import it.unipd.dei.eis.adapters.TheGuardianJsonAdapter;
import it.unipd.dei.eis.serialization.Deserializer;
import it.unipd.dei.eis.serialization.Serializer;

import java.io.File;
import java.io.IOException;

/**
 * Main for the Article Downloader
 * */


/**
 * Usage:
 * <p>
 * To Download articles use the following syntax:
 * java eis-final -d [source] [query|optional]</p>
 *<p>
 * To Download and Analyze articles use the following syntax:
 * java eis-final -da [source] [query|optional]
 *</p>
 * <p>
 * To Analyze articles use the following syntax:
 * java eis-final
 *</p>
 *
 * <p>Sources:
 * -nytimes
 * -theguardianapi
 * -theguardianlocal
 * </p>
 *
 *
 *
 *<p>
 * To add a source to the program:
 * -Create an Adapter in the "src/main/java/it.unipd.dei.eis/adapters folder"
 * -Add a case to the switch case in the main with the custom source name (e.g. case "new-source": [adapter-code];break;)
 * </p>
 */
public class Main {

  public static void main(String[] args) throws IOException {
    Serializer serializer = new Serializer();
    Deserializer deserializer = new Deserializer();

    //File yourFile = new File("articles.xml");
    //yourFile.createNewFile(); // if file already exists will do nothing

    //serializer.createFile("articles.xml");

    Article[] articles = deserializer.deserialize("articles.xml");




    if (args[0].equals("-d") || args[0].equals("-da")) {
      switch(args[1]){
        case "nytimes":
          NyTimesCsvAdapter nyTimesCsvAdapter = new NyTimesCsvAdapter();
          nyTimesCsvAdapter.loadArticles();
          serializer.serialize(nyTimesCsvAdapter.getArticles(), "articles.xml");

          System.out.println(articles.length + " articles in the XML file");
          if (args[0].equals("-da")) {
            Analyzer.main(null);
          }
          break;

        case "theguardianapi":
          TheGuardianJsonAdapter theGuardianJsonAdapter1 = new TheGuardianJsonAdapter();
          theGuardianJsonAdapter1.loadArticles();
          serializer.serialize(theGuardianJsonAdapter1.getArticles(), "articles.xml");

          System.out.println(articles.length + " articles in the XML file");
          if (args[0].equals("-da")) {
            Analyzer.main(null);
          }
          break;
        case "local":
          TheGuardianJsonAdapter theGuardianJsonAdapter2 = new TheGuardianJsonAdapter();
          theGuardianJsonAdapter2.callApi();
          serializer.serialize(theGuardianJsonAdapter2.getArticles(), "articles.xml");

          System.out.println(articles.length + " articles in the XML file");
          if (args[0].equals("-da")) {
            Analyzer.main(null);
          }
          break;
      }
    }
    else
    {
      Analyzer.main(null);
    }
  }
}