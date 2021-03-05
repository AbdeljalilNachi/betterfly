package com.betterfly.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.betterfly.web.rest.TestUtil;

public class ConstatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Constat.class);
        Constat constat1 = new Constat();
        constat1.setId(1L);
        Constat constat2 = new Constat();
        constat2.setId(constat1.getId());
        assertThat(constat1).isEqualTo(constat2);
        constat2.setId(2L);
        assertThat(constat1).isNotEqualTo(constat2);
        constat1.setId(null);
        assertThat(constat1).isNotEqualTo(constat2);
    }
}
