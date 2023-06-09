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
        // Create an array of articles
        Article[] articles = new Article[2];
        articles[0] = new Article("Title 1", "Body text 1");
        articles[1] = new Article("Title 2", "Body text 2");

        // Serialize the articles to XML
        serializer.serialize(articles);
    }

    /**
     * Test the deserialization of articles from XML.
     */
    @Test
    @Order(2)
    public void testDeserialize() {

        // check if file exists
        File file = new File("./assets/articles.xml");
        assertTrue(file.exists());

        // Deserialize the XML file and extract articles
        Article[] articles = deserializer.deserialize("./assets/articles.xml");

        // Verify the deserialized articles
        assertNotNull(articles);
        assertEquals(2, articles.length);

        // Verify the content of the articles
        assertEquals("Title 1", articles[0].getTitle());
        assertEquals("Body text 1", articles[0].getBodyText());

        assertEquals("Title 2", articles[1].getTitle());
        assertEquals("Body text 2", articles[1].getBodyText());

        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
