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

    @Test
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
        File outputFileObj = new File("./assets/output.txt");
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
            assertEquals("di=4", lines.get(0));
            assertEquals("test=4", lines.get(1));
            assertEquals("servono=3", lines.get(2));
            assertEquals("titolo=3", lines.get(3));
            assertEquals("altri=2", lines.get(4));
            assertEquals("ancora=2", lines.get(5));
            assertEquals("mi=2", lines.get(6));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createXMLFile() throws IOException {
        Serializer serializer = new Serializer();
        // Create an array of articles
        Article[] articles = new Article[4];
        articles[0] = new Article("Questo è un titolo di test", "Stiamo facendo dei test su questo file. Test test test... sto testando tutto mi servono ancora termini");
        articles[1] = new Article("Un altro titolo!", "Altro body text per altri test, devo arrivare anche a 50 termini. Aggiungo un pò di parole per non fare andare male il test. Test");
        articles[2] = new Article("Siamo al terzo articolo di test", "Ciao! Sono una descrizione per questo terzo oggetto articolo, mi servono ancora 9 parole!");
        articles[3] = new Article("Quarto titolo test", "Sono l'ultimo degli articoli di Test o ne servono altri? Mattia Bastianello Matteo Galiazzo Davide Bertini Matteo Rampin elementi di ingegneria del software");
        // Serialize the articles to XML
        serializer.serialize(articles);
    }
}

