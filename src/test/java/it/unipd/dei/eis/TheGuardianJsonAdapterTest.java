package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.NyTimesCsvAdapter;
import it.unipd.dei.eis.adapters.TheGuardianJsonAdapter;
import it.unipd.dei.eis.serialization.Deserializer;
import it.unipd.dei.eis.serialization.Serializer;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;
/**

 TheGuardianJsonAdapterTest is a JUnit test class that tests the functionality of the {@link TheGuardianJsonAdapter} class.
 It includes test cases for loading articles, retrieving articles, and comparing them with serialized/deserialized articles.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TheGuardianJsonAdapterTest {

    private TheGuardianJsonAdapter adapter;
    private Serializer serializer;
    private File tempFile = null;

    /**
     * Sets up the test environment by initializing the {@link TheGuardianJsonAdapter} and {@link Serializer} objects.
     */
    @BeforeEach
    public void setUp() {
        serializer = new Serializer();
        adapter = new TheGuardianJsonAdapter();
    }

    /**
     * Tests the {@link TheGuardianJsonAdapter#loadAllArticles()} method to ensure articles are loaded successfully.
     * It also verifies the content of the articles by comparing their titles and body texts.
     */
    @Test
    @Order(1)
    public void testLoadArticles() {
        // create temporary file
        try {
            tempFile = File.createTempFile("articles", ".xml", new File("./"));
        } catch (IOException e) {
            Assertions.fail("Failed to create temporary File");
            return;
        }

        adapter.loadAllArticles();
        serializer.serialize(adapter.getArticles(), tempFile.getAbsolutePath());

        // Verify that articles are loaded
        assertNotNull(adapter.getArticles());
        assertEquals(1000, adapter.getArticles().length);

        // Verify the content of the articles
        Article article1 = adapter.getArticles()[0];
        assertEquals("Germany’s last three nuclear power stations to shut this weekend", article1.getTitle());
        assertEquals("Germany’s three remaining nuclear power stations will shut down on Saturday, 12 years after the Fukushima disaster in Japan accelerated the country’s exit from atomic energy. The closures mark the conclusion of a stop-start approach to atomic energy and a victory for the country’s vociferous anti-nuclear movement. The facilities shutting are in Emsland, in the northern state of Lower Saxony, the Isar 2 site in Bavaria, and Neckarwestheim, in Baden-Württemberg in the south-west. The shutdowns leave a conundrum for energy policymakers attempting to balance growing electricity demand in one of Europe’s industrial superpowers and efforts to decarbonise, against the backdrop of uncertainty caused by the war in Ukraine. Germany last year delayed the closure of the three sites – which provided about 6.5% of the country’s electricity in 2022 – after Russia reduced European gas supplies, triggering concerns about a shortage of energy over the winter. The country began phasing out nuclear power more than two decades ago amid a long-fought campaign against the technology, but, in 2010 Angela Merkel, then chancellor, announced an extension to the life of the country’s 17 nuclear plants until 2036 at the latest. This policy was swiftly reversed the following year after an earthquake and tsunami caused the meltdown of reactors at the Fukushima Daiichi nuclear plant in Japan, triggering fresh anti-nuclear protests and political resolve to exit the technology. Nuclear accidents at Three Mile Island in the US in 1979 and Chernobyl in 1986 had already entrenched the push against nuclear in Germany, which had begun earlier in the 1970s. Germany has switched off 16 reactors since 2003. The final shutdowns have raised questions about security of energy supplies and the outlook for Germany’s carbon emissions. The country plans to close all coal-fired power plants by 2038, with the first round of closures planned in 2030. However, its parliament approved emergency legislation to reopen mothballed coal-fired power plants to aid electricity generation last year. A push to build more terminals to import liquefied natural gas has also been accelerated since the Ukraine war began. Coal accounted for just over 30% of Germany’s electricity generation in 2022, ahead of wind – responsible for 22%, gas-fired generation at 13% and solar at 10%. Biomass, nuclear and hydroelectric power made up the bulk of the remainder. The thinktank Ember has estimated that Germany and Poland will be the EU’s two largest producers of coal-fired electricity in 2030, responsible for more than half of EU power sector emissions by that point. Advocates of nuclear power argue that it provides a low-carbon, reliable alternative to fossil fuels for electricity generation. Critics say new projects are costly, frequently delayed and present environmental concerns over the disposal of nuclear waste. Tom Greatrex, chief executive of the UK’s Nuclear Industry Association, said the phaseout would worsen carbon emissions and “for a country supposedly renowned for its logical and evidence-driven approach is environmentally damaging, economically illiterate and deeply irresponsible”. He added: “At a time of heightened concern about energy security, Germany will be abandoning assets that can displace 34bn cubic metres of gas a year.” But Tom Burke, chair of the thinktank E3G, played down fears over energy security, and said a mild winter and high levels of gas storage in Europe meant concerns about power supplies next winter had eased. He said Germany’s renewables industry was growing and that improving grid connections and battery storage across the country would be key to moving the country’s energy system away from fossil fuels. “The coal is mainly being burnt for social and economic reasons, making sure the industry doesn’t shut down all at once,” said Burke. “You cannot have a technology transformation without a social transformation.” Germany’s abandonment of nuclear power is in contrast to the stated ambitions of the UK government, which last month kickstarted a fresh push into nuclear. Ministers have set up a delivery body for new nuclear projects and are running a competition for small nuclear reactors. However, progress on developing two existing large projects, Sizewell C in Suffolk and Hinkley Point C in Somerset, has been slow, with the latter delayed and overbudget.", article1.getBodyText());

        Article article2 = adapter.getArticles()[1];
        assertEquals("Leaks from Minnesota nuclear power plant raise safety fears across US", article2.getTitle());
        assertEquals("In December, Janica Jammes started a microgreens business in the basement of her home in Big Lake, Minnesota, just across the river from Xcel Energy’s nuclear plant in Monticello. At least once each day, she uses water from her well to nourish the plant trays. She delivers her product to customers within a 10-mile radius and says the business has been a success. But now she worries that her water could be contaminated by a leak of about 400,000 gallons of radioactive water that occurred in November at the plant, which is about 40 miles north-west of Minneapolis. Moreover, Jammes is upset that the company did not alert the public about the leak until March – and then detected a second leak, which the company described as smaller than the first one. “We don’t know for sure if” side-effects from the leaks “will happen or when anything will happen but just the lack of transparency is very concerning”, said Jammes, a 36-year-old mother of four. While Xcel Energy representatives have said the leaks did not affect local drinking water or pose a safety threat to residents, residents such as Jammes want more answers from the company. Independent nuclear energy experts agree that the company should have been more transparent, but they say that based on reports from state and federal agencies, they also do not think the leaks pose a health risk to residents or that the incidents will serve as a significant setback to efforts to promote the carbon-free power source in the US. “This leak, even though it was contained and poses no danger”, according to the official reports, “it should be used as some sort of wake-up call”, said Najmedin Meshkati, an engineering professor who specializes in nuclear safety at the University of Southern California. While some scientists see increasing nuclear energy as a crucial, safe way to reducing carbon emissions and increasing the country’s energy independence, the disasters at the Chernobyl, Three Mile Island and Fukushima nuclear power plants continue to cause fears of the power source. “Nuclear is the only clean energy sector that has the capacity to” transition away from fossil fuels “on a large scale”, said Charlyne Smith, a senior nuclear energy analyst at the Breakthrough Institute, an energy thinktank. “It is an industry that is highly scrutinized compared to other industries, and I think the Nuclear Regulatory Commission does a really good job at ensuring that safety is something that is practiced in the industry.” Even though Xcel did not announce the leak publicly, they notified the Regulatory Commission, which is a federal agency, and the state and in November, according to the Minnesota Pollution Control Agency. The company reported that about 400,000 gallons of water containing tritium leaked from a pipe at the facility. The regulators concluded that the spill had not reached the Mississippi River or contaminated drinking water sources near the plant. “While we immediately informed state and federal agencies, with no immediate safety risk, we focused on investigating the situation and containing the affected water in concert with our regulatory agencies,” Kevin Coss, an Xcel Energy spokesperson, stated in an email to the Guardian. “Making the announcement when we did allowed us to provide the public a more accurate and complete understanding of the leak and our plan to resolve it.” Smith said she agreed that the leak did not pose a significant safety risk but “learning about it months after really doesn’t help the industry”. After the announcement, Xcel held two open houses about the leaks. The company also shut the plant down after discovering the second leak but said it would reopen this week. Jammes was among hundreds of people to attend the meetings. She and others said they were frustrated that there was no presentation about the leaks and that company representatives just stood at tables and answered only some of attendees’ questions. “It was just a quick question and answer sort of thing, like if you have questions, then we’ll try to answer them, but it was very much: you’re going to hear what we want you to hear,” Jammes said. She wants to know why the pipe broke and what Xcel will do to prevent such accidents. Michael Voll, a 60-year-old warehouse associate, also criticized the company’s approach at the meetings. He has lived in Monticello for most his life and said Xcel, which opened the plant in 1971, has benefited the local economy. “You didn’t have to come from a nuclear submarine. You could go out there, and if your uncle or your dad worked there, you probably were going to get a job,” Voll said. He also remains a supporter of nuclear energy and has never feared the plant. But after the leaks and the public meetings, he said his trust in Xcel “is way down”. Xcel sent the pipe that leaked to an independent group, where experts are studying it to determine what caused it to fail, Coss, the Xcel spokesperson stated. “The results will help us understand whether there are other potential steps we need to take at the plant,” he wrote. The company also will conduct a “thorough inspection” of the plant while it’s offline for refueling this month, Coss added. Xcel “will work to maintain” the community’s trust, Coss wrote, “by thoroughly cleaning up the leaked tritium and providing prompt updates if anything about the situation changes”.", article2.getBodyText());

        // Delete temporary file
        try {
            tempFile.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the {@link TheGuardianJsonAdapter#getArticles()} method.
     * It verifies that the array of articles is correctly returned.
     */
    @Test
    public void testGetArticles() {
        Article[] articles = adapter.getArticles();
        assertNotNull(articles); // ensure that the array of articles is not null
        assertEquals(0, articles.length); // ensure that the array of articles is empty if loadArticles has not been called yet

        adapter.loadAllArticles();
        articles = adapter.getArticles();
        assertNotNull(articles); // ensure that the array of articles is not null
        assertTrue(articles.length > 0); // ensure that the array of articles is not empty after loadArticles has been called
    }

    /**
     * Test the {@link TheGuardianJsonAdapter#callApi()} method.
     *
     */
    @Test
    public void callApiTest() {
        try {
            // Create a temporary directory
            Path path = Paths.get("./tempDir");
            Files.createDirectories(path);

            //TODO: la directory la crea in un percorso esterno al progetto, fare in modo che il percorso sia il root del progetto

            TheGuardianJsonAdapter adapterForApi = new TheGuardianJsonAdapter("./tempDir/");

            adapterForApi.callApi(5);

            File directory = new File("tempDir");
            File[] files = directory.listFiles();
            int fileCount = files.length;
            assertEquals(5, fileCount);

            // Delete the temporary directory and its contents
            Path p = Paths.get("./tempDir");
            deleteDirectory(p);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void deleteDirectory(Path directory) throws IOException {
        Files.walk(directory)
                .sorted((p1, p2) -> -p1.compareTo(p2))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

}
