package com.betterfly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.betterfly.domain.enumeration.Rubrique;

/**
 * A ObligationConformite.
 */
@Entity
@Table(name = "obligation_conformite")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "obligationconformite")
public class ObligationConformite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "rubrique")
    private Rubrique rubrique;

    @Column(name = "reference")
    private String reference;

    @Column(name = "num")
    private Integer num;

    @Column(name = "exigence")
    private String exigence;

    @Column(name = "applicable")
    private Boolean applicable;

    @Column(name = "conforme")
    private Boolean conforme;

    @Column(name = "statut")
    private Integer statut;

    @Column(name = "observation")
    private String observation;

    @Column(name = "processus")
    private String processus;

    @Column(name = "o_rigine")
    private String oRIGINE;

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

    public ObligationConformite date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Rubrique getRubrique() {
        return rubrique;
    }

    public ObligationConformite rubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
        return this;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
    }

    public String getReference() {
        return reference;
    }

    public ObligationConformite reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getNum() {
        return num;
    }

    public ObligationConformite num(Integer num) {
        this.num = num;
        return this;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getExigence() {
        return exigence;
    }

    public ObligationConformite exigence(String exigence) {
        this.exigence = exigence;
        return this;
    }

    public void setExigence(String exigence) {
        this.exigence = exigence;
    }

    public Boolean isApplicable() {
        return applicable;
    }

    public ObligationConformite applicable(Boolean applicable) {
        this.applicable = applicable;
        return this;
    }

    public void setApplicable(Boolean applicable) {
        this.applicable = applicable;
    }

    public Boolean isConforme() {
        return conforme;
    }

    public ObligationConformite conforme(Boolean conforme) {
        this.conforme = conforme;
        return this;
    }

    public void setConforme(Boolean conforme) {
        this.conforme = conforme;
    }

    public Integer getStatut() {
        return statut;
    }

    public ObligationConformite statut(Integer statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    public String getObservation() {
        return observation;
    }

    public ObligationConformite observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getProcessus() {
        return processus;
    }

    public ObligationConformite processus(String processus) {
        this.processus = processus;
        return this;
    }

    public void setProcessus(String processus) {
        this.processus = processus;
    }

    public String getoRIGINE() {
        return oRIGINE;
    }

    public ObligationConformite oRIGINE(String oRIGINE) {
        this.oRIGINE = oRIGINE;
        return this;
    }

    public void setoRIGINE(String oRIGINE) {
        this.oRIGINE = oRIGINE;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObligationConformite)) {
            return false;
        }
        return id != null && id.equals(((ObligationConformite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObligationConformite{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", rubrique='" + getRubrique() + "'" +
            ", reference='" + getReference() + "'" +
            ", num=" + getNum() +
            ", exigence='" + getExigence() + "'" +
            ", applicable='" + isApplicable() + "'" +
            ", conforme='" + isConforme() + "'" +
            ", statut=" + getStatut() +
            ", observation='" + getObservation() + "'" +
            ", processus='" + getProcessus() + "'" +
            ", oRIGINE='" + getoRIGINE() + "'" +
            "}";
    }
}
