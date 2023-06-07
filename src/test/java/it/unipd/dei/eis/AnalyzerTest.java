package it.unipd.dei.eis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;

public class AnalyzerTest {

    @Test
    public void testAnalyzer() {
        // Create a temporary directory to store the output file
        File tempDir;
        try {
            tempDir = createTempDirectory();
        } catch (IOException e) {
            Assertions.fail("Failed to create temporary directory");
            return;
        }

        // Set the output file path to the temporary directory
        String outputFilePath = tempDir.getAbsolutePath() + File.separator + "output.txt";

        // Redirect System.out to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Call the Analyzer's main method
        Analyzer.main(new String[0]);

        // Restore System.out
        System.setOut(originalOut);

        // Verify the output file
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFilePath))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            // Perform your assertions on the output lines
            // For example, check that the first line is as expected
            Assertions.assertEquals("example: 10", lines.get(0));
        } catch (IOException e) {
            Assertions.fail("Failed to read the output file");
        }

        // Clean up the temporary directory
        deleteTempDirectory(tempDir);
    }

    private File createTempDirectory() throws IOException {
        File tempDir = File.createTempFile("temp", Long.toString(System.nanoTime()));
        if (!tempDir.delete() || !tempDir.mkdir()) {
            throw new IOException("Failed to create temp directory: " + tempDir.getAbsolutePath());
        }
        return tempDir;
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
