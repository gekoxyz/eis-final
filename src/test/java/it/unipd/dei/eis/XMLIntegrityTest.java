package it.unipd.dei.eis;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class XMLIntegrityTest {

    @Test
    public void testXMLIntegrity() {
        // Specify the path to your XML file
        String xmlFilePath = "articles.xml";

        try {
            // Create a DocumentBuilder to parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file
            builder.parse(new File(xmlFilePath));

            // If the parsing succeeds, the XML is valid
            assertTrue(true);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // If any exception occurs, the XML is invalid
            fail("XML parsing failed: " + e.getMessage());
        }
    }
}
