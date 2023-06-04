package it.unipd.dei.eis;


import it.unipd.dei.eis.serialization.Deserializer;
import it.unipd.dei.eis.serialization.Serializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Unit tests for the Serializer and Deserializer classes.
 */
public class SerializerDeserializerTest {

    private Serializer serializer;
    private Deserializer deserializer;

    /**
     * Set up the test environment before each test method.
     */
    @Before
    public void setUp() {
        serializer = new Serializer();
        deserializer = new Deserializer();
    }

    /**
     * Test the serialization of articles to XML.
     */
    @Test
    @Order(1)
    public void testSerialize() {
        // Create an array of articles
        Article[] articles = new Article[2];
        articles[0] = new Article("Title 1", "Body text 1");
        articles[1] = new Article("Title 2", "Body text 2");

        // Serialize the articles to XML
        serializer.serialize(articles, "test.xml");
    }

    /**
     * Test the deserialization of articles from XML.
     */
    @Test
    @Order(2)
    public void testDeserialize() {

        // Define the XML file to deserialize
        String fileName = "test.xml";

        // check if file exists
        File file = new File("test.xml");
        assertTrue(file.exists());

        // Deserialize the XML file and extract articles
        Article[] articles = deserializer.deserialize(fileName);

        // Verify the deserialized articles
        assertNotNull(articles);
        assertEquals(2, articles.length);

        // Verify the content of the articles
        assertEquals("Title 1", articles[0].getTitle());
        assertEquals("Body text 1", articles[0].getBodyText());

        assertEquals("Title 2", articles[1].getTitle());
        assertEquals("Body text 2", articles[1].getBodyText());

        if (file.delete()) {
            System.out.println("Deleted the file: " + file.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}
