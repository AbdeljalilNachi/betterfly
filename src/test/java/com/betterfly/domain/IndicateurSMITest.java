package com.betterfly.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.betterfly.web.rest.TestUtil;

public class IndicateurSMITest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndicateurSMI.class);
        IndicateurSMI indicateurSMI1 = new IndicateurSMI();
        indicateurSMI1.setId(1L);
        IndicateurSMI indicateurSMI2 = new IndicateurSMI();
        indicateurSMI2.setId(indicateurSMI1.getId());
        assertThat(indicateurSMI1).isEqualTo(indicateurSMI2);
        indicateurSMI2.setId(2L);
        assertThat(indicateurSMI1).isNotEqualTo(indicateurSMI2);
        indicateurSMI1.setId(null);
        assertThat(indicateurSMI1).isNotEqualTo(indicateurSMI2);
    }
}
