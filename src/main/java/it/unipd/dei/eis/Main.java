package it.unipd.dei.eis;

import com.opencsv.exceptions.CsvValidationException;
import it.unipd.dei.eis.adapters.NyTimesCsvAdapter;
import it.unipd.dei.eis.adapters.TheGuardianJsonAdapter;
import it.unipd.dei.eis.serialization.Serializer;
import it.unipd.dei.eis.serialization.Deserializer;


public class Main {

  public static void main(String[] args) {
    Serializer serializer = new Serializer();
    Deserializer deserializer = new Deserializer();

    /*
     * -download
     * java eis-final -d [sorgente] [query]
     *
     * -download e analisi
     * java eis-final -da [sorgente] [query]
     *
     * -analisi
     * java eis-final
     * */


    if(args[0].equals("-d") || args[0].equals("-da"))
      if(args[1].equals("nytimes"))
      {
        NyTimesCsvAdapter nyTimesCsvAdapter = new NyTimesCsvAdapter();
        nyTimesCsvAdapter.loadArticles();
        serializer.serialize(nyTimesCsvAdapter.getArticles(), "articles.xml");



        Article[] articles = deserializer.deserialize("articles.xml");
        System.out.println(articles.length + " articles in the XML file");
        if(args[0].equals("-da")) {
          Analyzer.main(null);
        }
      }
      else if(args[1].equals("theguardian"))
      {
        TheGuardianJsonAdapter theGuardianJsonAdapter1 = new TheGuardianJsonAdapter();
        theGuardianJsonAdapter1.loadArticles();
        serializer.serialize(theGuardianJsonAdapter1.getArticles(), "articles.xml");

        Article[] articles = deserializer.deserialize("articles.xml");
        System.out.println(articles.length + " articles in the XML file");

        if(args[0].equals("-da")) {
          Analyzer.main(null);
        }
      }

      else
      {
        Analyzer.main(null);
      }











      /*NyTimesCsvAdapter nyTimesCsvAdapter2 = new NyTimesCsvAdapter("./assets/nytimes/temp.csv");
      try {
        nyTimesCsvAdapter2.loadArticles();
        serializer.serialize(nyTimesCsvAdapter2.getArticles(),"articles.xml");
      } catch (CsvValidationException e) {
          throw new RuntimeException(e);
      }*/



      /*TheGuardianJsonAdapter theGuardianJsonAdapter = new TheGuardianJsonAdapter("./assets/the_guardian/theguardian_articles_v1_p01.json");
      theGuardianJsonAdapter.loadArticles();

      serializer.serialize(theGuardianJsonAdapter.getArticles(), "articles.xml");*/



  }
}