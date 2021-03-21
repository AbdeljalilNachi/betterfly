package com.betterfly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.betterfly.domain.enumeration.Standard;

/**
 * A PlanificationRDD.
 */
@Entity
@Table(name = "planification_rdd")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "planificationrdd")
public class PlanificationRDD implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "n_rdd")
    private Integer nRdd;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "realisee")
    private Boolean realisee;

    @Lob
    @Column(name = "presentation")
    private byte[] presentation;

    @Column(name = "presentation_content_type")
    private String presentationContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "standard")
    private Standard standard;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getnRdd() {
        return nRdd;
    }

    public PlanificationRDD nRdd(Integer nRdd) {
        this.nRdd = nRdd;
        return this;
    }

    public void setnRdd(Integer nRdd) {
        this.nRdd = nRdd;
    }

    public LocalDate getDate() {
        return date;
    }

    public PlanificationRDD date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isRealisee() {
        return realisee;
    }

    public PlanificationRDD realisee(Boolean realisee) {
        this.realisee = realisee;
        return this;
    }

    public void setRealisee(Boolean realisee) {
        this.realisee = realisee;
    }

    public byte[] getPresentation() {
        return presentation;
    }

    public PlanificationRDD presentation(byte[] presentation) {
        this.presentation = presentation;
        return this;
    }

    public void setPresentation(byte[] presentation) {
        this.presentation = presentation;
    }

    public String getPresentationContentType() {
        return presentationContentType;
    }

    public PlanificationRDD presentationContentType(String presentationContentType) {
        this.presentationContentType = presentationContentType;
        return this;
    }

    public void setPresentationContentType(String presentationContentType) {
        this.presentationContentType = presentationContentType;
    }

    public Standard getStandard() {
        return standard;
    }

    public PlanificationRDD standard(Standard standard) {
        this.standard = standard;
        return this;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanificationRDD)) {
            return false;
        }
        return id != null && id.equals(((PlanificationRDD) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanificationRDD{" +
            "id=" + getId() +
            ", nRdd=" + getnRdd() +
            ", date='" + getDate() + "'" +
            ", realisee='" + isRealisee() + "'" +
            ", presentation='" + getPresentation() + "'" +
            ", presentationContentType='" + getPresentationContentType() + "'" +
            ", standard='" + getStandard() + "'" +
            "}";
    }
}
