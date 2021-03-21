package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PolitiqueQSE.
 */
@Entity
@Table(name = "politique_qse")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "politiqueqse")
public class PolitiqueQSE implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "axe_politique_qse")
    private String axePolitiqueQSE;

    @Column(name = "objectif_qse")
    private String objectifQSE;

    @Column(name = "vigueur")
    private Boolean vigueur;

    @ManyToOne
    @JsonIgnoreProperties(value = "politiqueQSES", allowSetters = true)
    private ProcessusSMI processus;

    @ManyToOne
    @JsonIgnoreProperties(value = "politiqueQSES", allowSetters = true)
    private IndicateurSMI indicateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public PolitiqueQSE date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAxePolitiqueQSE() {
        return axePolitiqueQSE;
    }

    public PolitiqueQSE axePolitiqueQSE(String axePolitiqueQSE) {
        this.axePolitiqueQSE = axePolitiqueQSE;
        return this;
    }

    public void setAxePolitiqueQSE(String axePolitiqueQSE) {
        this.axePolitiqueQSE = axePolitiqueQSE;
    }

    public String getObjectifQSE() {
        return objectifQSE;
    }

    public PolitiqueQSE objectifQSE(String objectifQSE) {
        this.objectifQSE = objectifQSE;
        return this;
    }

    public void setObjectifQSE(String objectifQSE) {
        this.objectifQSE = objectifQSE;
    }

    public Boolean isVigueur() {
        return vigueur;
    }

    public PolitiqueQSE vigueur(Boolean vigueur) {
        this.vigueur = vigueur;
        return this;
    }

    public void setVigueur(Boolean vigueur) {
        this.vigueur = vigueur;
    }

    public ProcessusSMI getProcessus() {
        return processus;
    }

    public PolitiqueQSE processus(ProcessusSMI processusSMI) {
        this.processus = processusSMI;
        return this;
    }

    public void setProcessus(ProcessusSMI processusSMI) {
        this.processus = processusSMI;
    }

    public IndicateurSMI getIndicateur() {
        return indicateur;
    }

    public PolitiqueQSE indicateur(IndicateurSMI indicateurSMI) {
        this.indicateur = indicateurSMI;
        return this;
    }

    public void setIndicateur(IndicateurSMI indicateurSMI) {
        this.indicateur = indicateurSMI;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolitiqueQSE)) {
            return false;
        }
        return id != null && id.equals(((PolitiqueQSE) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PolitiqueQSE{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", axePolitiqueQSE='" + getAxePolitiqueQSE() + "'" +
            ", objectifQSE='" + getObjectifQSE() + "'" +
            ", vigueur='" + isVigueur() + "'" +
            "}";
    }
}
