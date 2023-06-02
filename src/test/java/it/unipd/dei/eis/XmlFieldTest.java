package it.unipd.dei.eis;import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.*;

public class XmlFieldTest {

    @Test
    public void testXmlContainsTagMultipleTimes() throws Exception {
        // The XML document source file path
        String xmlFilePath = "articles.xml";

        // The tag to check for in the XML document
        String tagToCheck = "bodyText";

        // The expected number of occurrences of the tag
        int expectedOccurrences = 1000;

        try {
            // Create a new DocumentBuilder to parse the XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Read the XML file
            File xmlFile = new File(xmlFilePath);
            FileInputStream fileInputStream = new FileInputStream(xmlFile);

            // Parse the XML document
            Document document = builder.parse(new InputSource(fileInputStream));

            // Get the root element of the document
            Node root = document.getDocumentElement();

            // Count the occurrences of the tag in the XML document
            int tagOccurrences = countTagOccurrences(root, tagToCheck);

            // Assert that the tag occurs the expected number of times in the XML document
            assertEquals("The XML document does not contain the tag '" + tagToCheck + "' " + expectedOccurrences + " times", expectedOccurrences, tagOccurrences);
        } catch (Exception e) {
            // Handle any exception that occurred during XML parsing or file reading
            fail("An exception occurred: " + e.getMessage());
        }
    }

    private int countTagOccurrences(Node node, String tagName) {
        int count = 0;
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(tagName)) {
            count++;
        }
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            count += countTagOccurrences(child, tagName);
        }
        return count;
    }
}
