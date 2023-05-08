package it.matteogaliazzo.eis;

import com.opencsv.CSVReader;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

public class Main {
    public static void main(String[] args) throws IOException, CsvException {

        // get path of csv file
        Path path = Paths.get("files/nytimes_articles_v1.csv");
        // convert Path to String
        String s_path = path.toAbsolutePath().toString();
        // get info from csv file and print to terminal

        CSVReader reader = null;
        try {
        //parsing a CSV file into CSVReader class constructor
            reader = new CSVReader(new FileReader(s_path));
            String [] nextLine;
            //reads one line at a time
            while ((nextLine = reader.readNext()) != null) {
                for(String token : nextLine) {
                    System.out.println(token);
                }
                System.out.print("\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
