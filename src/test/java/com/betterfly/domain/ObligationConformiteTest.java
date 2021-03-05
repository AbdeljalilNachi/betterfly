package com.betterfly.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.betterfly.web.rest.TestUtil;

public class ObligationConformiteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObligationConformite.class);
        ObligationConformite obligationConformite1 = new ObligationConformite();
        obligationConformite1.setId(1L);
        ObligationConformite obligationConformite2 = new ObligationConformite();
        obligationConformite2.setId(obligationConformite1.getId());
        assertThat(obligationConformite1).isEqualTo(obligationConformite2);
        obligationConformite2.setId(2L);
        assertThat(obligationConformite1).isNotEqualTo(obligationConformite2);
        obligationConformite1.setId(null);
        assertThat(obligationConformite1).isNotEqualTo(obligationConformite2);
    }
}
