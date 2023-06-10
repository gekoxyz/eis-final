package it.unipd.dei.eis;

import it.unipd.dei.eis.serialization.Serializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnalyzerTest {

    private Analyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new Analyzer();
    }

    //@Test
    void testAnalyze() {
        // Create a sample input file
        try {
            createXMLFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Call the analyze() method
        analyzer.analyze();

        // Verify that the output file is created
        File outputFileObj = new File("output.txt");
        assertTrue(outputFileObj.exists());

        // Verify the content of the output file
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFileObj))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            // Add your assertions here
            // Verify the expected lines in the output file
            assertEquals("word1: count1", lines.get(0));
            assertEquals("word2: count2", lines.get(1));
            // ...
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createXMLFile() throws IOException {
        Serializer serializer = new Serializer();
        File myObj = new File("articles.xml");
        try {
            myObj.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create an array of articles
        Article[] articles = new Article[2];
        articles[0] = new Article("Questo è un titolo di test", "Stiamo facendo dei test su questo file. Test test test...");
        articles[1] = new Article("Un altro titolo!", "Altro body text per altri test");

        // Serialize the articles to XML
        serializer.serialize(articles, myObj.getAbsolutePath());
    }
}

