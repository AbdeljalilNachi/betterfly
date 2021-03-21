package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A BesoinPI.
 */
@Entity
@Table(name = "besoin_pi")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "besoinpi")
public class BesoinPI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_identification")
    private LocalDate dateIdentification;

    @Column(name = "pi_pertinentes")
    private String piPertinentes;

    @Column(name = "pertinente")
    private Boolean pertinente;

    @Column(name = "prise_en_charge")
    private Boolean priseEnCharge;

    @Column(name = "afficher")
    private Boolean afficher;

    @ManyToOne
    @JsonIgnoreProperties(value = "besoinPIS", allowSetters = true)
    private ProcessusSMI processus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateIdentification() {
        return dateIdentification;
    }

    public BesoinPI dateIdentification(LocalDate dateIdentification) {
        this.dateIdentification = dateIdentification;
        return this;
    }

    public void setDateIdentification(LocalDate dateIdentification) {
        this.dateIdentification = dateIdentification;
    }

    public String getPiPertinentes() {
        return piPertinentes;
    }

    public BesoinPI piPertinentes(String piPertinentes) {
        this.piPertinentes = piPertinentes;
        return this;
    }

    public void setPiPertinentes(String piPertinentes) {
        this.piPertinentes = piPertinentes;
    }

    public Boolean isPertinente() {
        return pertinente;
    }

    public BesoinPI pertinente(Boolean pertinente) {
        this.pertinente = pertinente;
        return this;
    }

    public void setPertinente(Boolean pertinente) {
        this.pertinente = pertinente;
    }

    public Boolean isPriseEnCharge() {
        return priseEnCharge;
    }

    public BesoinPI priseEnCharge(Boolean priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
        return this;
    }

    public void setPriseEnCharge(Boolean priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
    }

    public Boolean isAfficher() {
        return afficher;
    }

    public BesoinPI afficher(Boolean afficher) {
        this.afficher = afficher;
        return this;
    }

    public void setAfficher(Boolean afficher) {
        this.afficher = afficher;
    }

    public ProcessusSMI getProcessus() {
        return processus;
    }

    public BesoinPI processus(ProcessusSMI processusSMI) {
        this.processus = processusSMI;
        return this;
    }

    public void setProcessus(ProcessusSMI processusSMI) {
        this.processus = processusSMI;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BesoinPI)) {
            return false;
        }
        return id != null && id.equals(((BesoinPI) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BesoinPI{" +
            "id=" + getId() +
            ", dateIdentification='" + getDateIdentification() + "'" +
            ", piPertinentes='" + getPiPertinentes() + "'" +
            ", pertinente='" + isPertinente() + "'" +
            ", priseEnCharge='" + isPriseEnCharge() + "'" +
            ", afficher='" + isAfficher() + "'" +
            "}";
    }
}
