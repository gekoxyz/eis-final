package it.unipd.dei.eis;


import it.unipd.dei.eis.adapters.NyTimesCsvAdapter;
import it.unipd.dei.eis.serialization.Deserializer;
import org.junit.Test;


import static org.junit.Assert.*;

public class NyTimesCsvAdapterTest {

    @Test
    public void testLoadArticles() {
        NyTimesCsvAdapter adapter = new NyTimesCsvAdapter(); // use test folder for csv files
        adapter.loadArticles();
        Article[] articles = adapter.getArticles(); // CSV article

        int numArticles = 1000; // numArticles in CSV file
        assertEquals(numArticles, articles.length); // ensure that articles in CSV file were loaded

        // TODO: get every title and bodyText from CSV and compare with XML title that we have
        // TODO: check xml format
        // deserialize local xml
        Deserializer xml_dsl = new Deserializer();
        Article[] a = xml_dsl.deserialize("./articles.xml");

        // check if title and bodyText matches
        int i = 0;
        for(Article article : articles) {
            assertEquals(article.getTitle(),a[i].getTitle()); // ensure that the first article's title was parsed correctly
            assertEquals(article.getBodyText(), a[i].getBodyText()); // ensure that the first article's body text was parsed correctly
            i++;
        }
    }

    @Test
    public void testGetArticles() {
        NyTimesCsvAdapter adapter = new NyTimesCsvAdapter(); // use test folder for csv files
        Article[] articles = adapter.getArticles();
        assertNotNull(articles); // ensure that the array of articles is not null
        assertEquals(0, articles.length); // ensure that the array of articles is empty if loadArticles has not been called yet

        adapter.loadArticles();
        articles = adapter.getArticles();
        assertNotNull(articles); // ensure that the array of articles is not null
        assertTrue(articles.length > 0); // ensure that the array of articles is not empty after loadArticles has been called
    }
}