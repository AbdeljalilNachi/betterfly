package com.betterfly.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.betterfly.web.rest.TestUtil;

public class ConstatAuditTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConstatAudit.class);
        ConstatAudit constatAudit1 = new ConstatAudit();
        constatAudit1.setId(1L);
        ConstatAudit constatAudit2 = new ConstatAudit();
        constatAudit2.setId(constatAudit1.getId());
        assertThat(constatAudit1).isEqualTo(constatAudit2);
        constatAudit2.setId(2L);
        assertThat(constatAudit1).isNotEqualTo(constatAudit2);
        constatAudit1.setId(null);
        assertThat(constatAudit1).isNotEqualTo(constatAudit2);
    }
}
