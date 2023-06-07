package it.unipd.dei.eis;


import it.unipd.dei.eis.serialization.Deserializer;
import it.unipd.dei.eis.serialization.Serializer;
import org.junit.AfterClass;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Unit tests for the Serializer and Deserializer classes.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SerializerDeserializerTest {

    private Serializer serializer;
    private Deserializer deserializer;
    private static File tempFile = null;

    /**
     * Set up the test environment before each test method.
     */
    @BeforeEach
    public void setUp() {
        serializer = new Serializer();
        deserializer = new Deserializer();
    }

    /**
     * Test the serialization of articles to XML.
     * Create a xml file inside test folder.
     */
    @Test
    @Order(1)
    public void testSerialize() {
        // Create a temporary directory to store the output file
        try {
            tempFile = File.createTempFile("test", ".xml", new File("./"));
        } catch (IOException e) {
            Assertions.fail("Failed to create temporary File");
            return;
        }

        // Create an array of articles
        Article[] articles = new Article[2];
        articles[0] = new Article("Title 1", "Body text 1");
        articles[1] = new Article("Title 2", "Body text 2");

        // Serialize the articles to XML
        serializer.serialize(articles, tempFile.getAbsolutePath());
    }

    /**
     * Test the deserialization of articles from XML.
     */
    @Test
    @Order(2)
    public void testDeserialize() {

        // check if file exists
        File file = new File(tempFile.getAbsolutePath());
        assertTrue(file.exists());

        // Deserialize the XML file and extract articles
        Article[] articles = deserializer.deserialize(tempFile.getAbsolutePath());

        // Verify the deserialized articles
        assertNotNull(articles);
        assertEquals(2, articles.length);

        // Verify the content of the articles
        assertEquals("Title 1", articles[0].getTitle());
        assertEquals("Body text 1", articles[0].getBodyText());

        assertEquals("Title 2", articles[1].getTitle());
        assertEquals("Body text 2", articles[1].getBodyText());

        // Clean up the temporary directory
        try {
            tempFile.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteTempDirectory(File tempDir) {
        if (tempDir != null) {
            File[] files = tempDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            tempDir.delete();
        }
    }
}
