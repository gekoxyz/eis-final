package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.TheGuardianJsonAdapter;
import org.junit.jupiter.api.Test;

public class AnalyzerTest {

    @Test
    public void testApi() {
        TheGuardianJsonAdapter adp = new TheGuardianJsonAdapter();

        adp.callApi(10);
    }
}
