package com.betterfly.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.betterfly.web.rest.TestUtil;

public class AnalyseSSTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyseSST.class);
        AnalyseSST analyseSST1 = new AnalyseSST();
        analyseSST1.setId(1L);
        AnalyseSST analyseSST2 = new AnalyseSST();
        analyseSST2.setId(analyseSST1.getId());
        assertThat(analyseSST1).isEqualTo(analyseSST2);
        analyseSST2.setId(2L);
        assertThat(analyseSST1).isNotEqualTo(analyseSST2);
        analyseSST1.setId(null);
        assertThat(analyseSST1).isNotEqualTo(analyseSST2);
    }
}
