package it.unipd.dei.eis;

import com.opencsv.bean.CsvBindByPosition;

/**
 * classe rubata (preso ispirazione) da the guardian api
 */

//Identifier,URL,Title,Fulltext,Date,Source Set,Source
public class NYTimesArticle {
    @CsvBindByPosition(position = 0)
    String id;
    @CsvBindByPosition(position = 1)
    String url;
    @CsvBindByPosition(position = 2)
    String title;
    @CsvBindByPosition(position = 3)
    String fullText;
    @CsvBindByPosition(position = 4)
    String date;
    @CsvBindByPosition(position = 5)
    String sourceSet;
    @CsvBindByPosition(position = 6)
    String source;
    public NYTimesArticle(final String id, final String url, final String title, final String fullText, final String date, final String sourceSet, final String source) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.fullText = fullText;
        this.date = date;
        this.sourceSet = sourceSet;
        this.source = source;
    }
    @Override
    public String toString() {
        return "";
    }
}
