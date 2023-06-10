package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.NyTimesCsvAdapter;
import it.unipd.dei.eis.serialization.Deserializer;
import it.unipd.dei.eis.serialization.Serializer;
import org.junit.jupiter.api.*;


import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * The {@code NyTimesCsvAdapterTest} class is a JUnit test class for the {@link NyTimesCsvAdapter} class.
 * It contains test methods to verify the functionality of the adapter.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NyTimesCsvAdapterTest {

    private NyTimesCsvAdapter adapter;
    private Serializer serializer;
    private File tempFile = null;
    @BeforeEach
    public void setUp() {
        serializer = new Serializer();
        adapter = new NyTimesCsvAdapter();
    }

    /**
     * Tests the {@link NyTimesCsvAdapter#loadAllArticles()} method to ensure articles are loaded successfully.
     * It also verifies the content of the articles by comparing their titles and body texts.
     */
    @Test
    @Order(1)
    public void testLoadArticles() {

        // create temporary file
//        try {
//            tempFile = File.createTempFile("articles", ".xml", new File("./"));
//        } catch (IOException e) {
//            Assertions.fail("Failed to create temporary File");
//            return;
//        }

        adapter.loadAllArticles();
        serializer.serialize(adapter.getArticles());

        // Verify that articles are loaded
        assertNotNull(adapter.getArticles());
        assertEquals(1000, adapter.getArticles().length);

        // Verify the content of the articles
        Article article1 = adapter.getArticles()[0];

        assertEquals("Hard Questions on Nuclear Power", article1.getTitle());
        String s = "After decades in the doghouse because of environmental, safety and cost concerns, nuclear power is enjoying a renaissance of expectations. The Bush administration's new energy plan gives a place of prominence to nuclear power as a clean and efficient energy source, and the industry itself is bubbling with new hopes and plans. In truth, there are good reasons to take a fresh look at this much-maligned source of energy that has been stalled in this country for the past three decades. But it is worrisome that the administration seems to have endorsed a nuclear resurgence with little sustained analysis of its pluses and minuses. As an article by Katharine Seelye in The Times revealed last week, nuclear energy was barely in the consciousness of the drill-centric energy team at the White House until a delegation of nuclear industry executives sought a chance to make their pitch and succeeded so well that Vice President Dick Cheney almost immediately started touting the virtues of nuclear energy. A case can be made for greater exploitation of nuclear power in this country, but before the nation plunges too far down this path the administration will need to address some critical questions. The rationale for a reassessment comes partly from the performance of the industry itself, and partly from changed circumstances in the environment in which it must operate. By most accounts, the industry has learned to operate its plants more safely and efficiently than in the years leading up to the traumatic near-tragedy at Three Mile Island. American nuclear plants are operating with much greater reliability and many fewer minor incidents. Moreover, the industry is consolidating, with plants being purchased and operated by companies that have more expertise than some of the previous operators. So there is reason to trust the industry a bit more than in past decades. Meanwhile, external events are increasing the appeal of nuclear power. One is the rising concern over global warming, which is caused primarily by the emission of carbon dioxide from burning fossil fuels. Nuclear power plants emit no carbon dioxide, thus to the extent they can replace plants burning coal, oil or natural gas they can be considered a plus. Nuclear power can also contribute to the diversity of the nation's energy supplies. Nuclear plants already supply some 20 percent of the electricity generated in this country, compared with fossil fuel contributions of 52 percent for coal, 16 percent for natural gas and 3 percent for oil. But the great majority of all new power plants are being built to burn natural gas, the cleanest of the fossil fuels, making utilities and consumers vulnerable to price spikes when supplies become tight as they have this year. President Bush's energy plan offers a wide range of steps to accelerate the use of nuclear power. But before Congress and the regulatory agencies proceed too far, some crucial questions require answers. Impact on global warming: If this is the main reason for turning to nuclear power, the proponents will need to do a much better job of spelling out just how far nuclear power would have to spread to make a real dent in the problem. Nuclear power is used almost exclusively to generate electricity, thus it cannot reduce the nation's reliance on imported oil to power transportation systems. Nuclear fuel will primarily be substituting for natural gas -- the least of the carbon dioxide emitters -- as the clean fuel to which electric utilities turn. Moreover, fossil fuels are burned in mining and preparing nuclear fuels and in building reactors, so even nuclear energy is not entirely free of greenhouse gases. Some analyses suggest that to make a real impact in slowing global warming, nuclear power plants would need to spread widely around the world, a prospect that brings new challenges of its own. Weapons risks: Expansion of nuclear power in this country poses no weapons danger, but the spread of nuclear plants into other countries could pose a risk. The uranium fuel for nuclear power plants is not generally considered of high enough grade to be used in weapons. But as more and more technicians around the world learn the skills of working with nuclear materials, and as governments become engaged in procuring nuclear technologies, there is a danger that civilian nuclear programs could serve as a cover for clandestine weapons activities. That is why, for example, the United States is angry that Russia is helping Iran build a nuclear power plant. Even though Iran has pledged to abide by nonproliferation treaties and allow international inspections of the plant, there is grave concern that it will find a way to build weapons. Increasing the use of nuclear power in countries that already have either the bomb or nuclear power plants is not much of a danger. Spreading nuclear power to additional countries might be. Waste disposal: In the political world, the lack of a proven method to store spent fuel from nuclear reactors for the tens of thousands of years the material remains radioactive has long been considered the Achilles' heel of the nuclear industry. In truth, spent fuel has been stored safely for decades in pools at the sites of nuclear power plants with no adverse effect. The problem is, the storage pools are filling up and critics are loath to expand nuclear power with no clear idea where to store the waste. The Bush administration is considering a site at Yucca Mountain in Nevada that has been studied for years, and it has proposed a new look at reprocessing the fuel to remove the long-lived plutonium for reuse as reactor fuel. That could greatly ease the storage problem here but might encourage wider use of reprocessed materials abroad, increasing the risk of weapons-grade plutonium's falling into the wrong hands. Reactor safety: The safety problem in conventional nuclear plants is that, if things start to go wrong, emergency cooling systems and human operators have to act correctly to prevent a catastrophic meltdown. That makes nuclear power a cruel and unforgiving technology that cannot tolerate equipment failures or human mistakes. But the industry is exploring new technologies that would not lead to meltdown even in a worst-case malfunction, making them inherently safer and cheaper to build and operate. This is where the administration and the industry should be focusing their efforts -- to develop demonstrably safer power plants. That would ease many of the concerns provoked by the current generation of nuclear reactors. Economics: No matter what else is done to make nuclear power more attractive, the industry will make little headway unless it can overcome the high capital costs that brought it to a halt in recent decades. Some relief should come from the advance approval of standardized designs, allowing plants to be built more quickly and cheaply than in past years when each plant had a customized design. But Congress will need to take a close look at whether it should renew one of the industry's economic underpinnings -- the so-called Price-Anderson Act that limits the liability of nuclear companies in the event of an accident. If the industry is as safe as it says, it may not need such subsidized protection. On the other hand, eliminating the liability protection might scare off investors for good. Nuclear power has been stalled for so long in the United States that it is surprising to see it back in the spotlight. There may be a case for extending the licenses of existing plants, as has already happened in several cases, or for building new plants on existing nuclear sites where the risks are already understood. But the case has not yet been made for truly large-scale expansion of nuclear power, in this country or around the globe.";
        assertEquals(s, article1.getBodyText());

        Article article3 = adapter.getArticles()[2];
        assertEquals("Nuclear Power Gains in Status After Lobbying", article3.getTitle());
        s = "As the White House was putting together the energy plan that President Bush released last week, there had been almost no talk of nuclear power as a component of the nation's energy strategy. The nuclear industry thought this was a glaring omission, and a handful of top nuclear industry officials decided they needed to take their case to the administration. In mid-March, a cadre of seven nuclear power executives sought and won an hourlong meeting in the White House with Karl Rove, Mr. Bush's top political adviser. Also attending were Lawrence B. Lindsey, the president's top economic adviser, Andrew Lundquist, the executive director of Vice President Dick Cheney's energy task force, and others involved in devising the energy plan. ''We said, Look, we are an important player on this energy team and here are our vital statistics, and we think that you should start talking about nuclear when you talk about increasing the nation's supply,'' Christian H. Poindexter, chairman of the Constellation Energy Group, recalled today. And then a surprising thing happened. ''It was shortly after that, as a matter of fact I think the next night, when the vice president was being interviewed on television, he began to talk about nuclear power for the first time,'' Mr. Poindexter said. Mr. Cheney first discussed nuclear power as an alternative to dirtier fossil fuels in a March 21 interview on CNBC. ''If you want to do something about carbon dioxide emissions,'' he said, ''then you ought to build nuclear power plants because they don't emit any carbon dioxide, they don't emit greenhouse gases.'' Mr. Cheney had missed the meeting with nuclear executives because he was on Capitol Hill, talking to members of Congress who themselves were pushing nuclear energy. In a quick chain reaction, Mr. Cheney put the long-maligned nuclear power industry back on the political map. In the energy plan released last week, the administration breathed new life into the industry, declaring nuclear technology, which provides 20 percent of the nation's electricity, much safer than it was 20 years ago. Today, Mr. Cheney appeared before 350 nuclear industry executives meeting in Washington -- 100 more than showed up at last year's annual meeting of the Nuclear Energy Institute -- and told them the administration wanted to encourage the Nuclear Regulatory Commission to expedite applications for new reactors, relicense existing plants and ''increase the resources devoted to safety and enforcement as we prepare to increase nuclear generating capacity in the future.'' He said the administration also wanted to renew the Price-Anderson Act, which limits nuclear plant operators' liability in case of an accident. Mr. Poindexter is still incredulous. ''In my wildest dreams, when I was over at the White House in March, I couldn't imagine them getting so behind us,'' he said. He was skeptical for good reason. Few industries have enjoyed the kind of renaissance that nuclear power may be poised to undergo. Accidents at Three Mile Island in Pennsylvania and Chernobyl in Ukraine seemed to seal the industry's fate as too dangerous, too uncontrollable and too expensive to win back a frightened public or secure the financial backing of Wall Street. The last nuclear power plant to enter operation was ordered in 1973. There still is no solution to the vexing problem of nuclear waste storage. And while recent polling shows that Americans more lopsidedly oppose dirtier fossil fuels than they oppose nuclear power, they still do not want to live near nuclear power plants. For those wary of a nuclear revival, these problems are no less real today than they were two decades ago. ''The Bush administration should at most be looking to proceed with what the Nuclear Regulatory Commission was planning -- an orderly phase-out of existing power plants,'' said Paul L. Leventhal, president of the Nuclear Control Institute and co-director of the Senate investigation into the 1979 accident at Three Mile Island. ''Instead, they're talking about a new rebirth, and it frankly just doesn't make sense.'' The Union of Concerned Scientists, using data from the industry itself, says that aging plants have experienced eight forced shutdowns in the last 16 months. And Mr. Leventhal said that replacing coal with nuclear power would not appreciably diminish global warming because most of the pollutants that cause global warming come from cars and trucks. Another problem, and one that Mr. Cheney fully acknowledges, is the lack of a national repository for the storage of nuclear waste. In his speech today, the vice president warned that the lack of a storage site could be a deal killer. Without a site, he said, ''eventually the contribution we can count on from the nuclear industry will, in fact, decline.'' The storage problem will not be solved at Yucca Mountain in Nevada, if Nevada politicians and the gambling industry have anything to say about it. Senators Harry Reid, a Democrat, and John Ensign, a Republican, have made opposition to nuclear waste dumping in their state their priority. ''Until they get the waste problem solved,'' Mr. Reid said, ''nothing's going to happen on nuclear power.'' Peter Bradford, a former member of the Nuclear Regulatory Commission, who now teaches energy policy at Yale, said that apart from the safety issues, nuclear power was economically problematic. ''The types of long-term investment necessary to sustain nuclear energy are going to prove very hard to find in this kind of volatile marketplace,'' Mr. Bradford said. Still, there are cheerleaders. One is Representative Billy Tauzin, the Louisiana Republican who heads the Energy and Commerce Committee. He spoke today at the Nuclear Energy Institute's annual meeting and summed up the surprise that others feel at the recent turn of events. ''As we gather here in Washington,'' Mr. Tauzin said, ''who would have thunk that we'd be discussing the possibility of nuclear construction in this country?''";
        assertEquals(s, article3.getBodyText());

        // Delete file
        File file = new File("./assets/articles.xml");
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            tempFile.deleteOnExit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Test the {@link NyTimesCsvAdapter#getArticles()} method.
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
}