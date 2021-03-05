package com.betterfly.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.betterfly.web.rest.TestUtil;

public class ProcessusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Processus.class);
        Processus processus1 = new Processus();
        processus1.setId(1L);
        Processus processus2 = new Processus();
        processus2.setId(processus1.getId());
        assertThat(processus1).isEqualTo(processus2);
        processus2.setId(2L);
        assertThat(processus1).isNotEqualTo(processus2);
        processus1.setId(null);
        assertThat(processus1).isNotEqualTo(processus2);
    }
}
