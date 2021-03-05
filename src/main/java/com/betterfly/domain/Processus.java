package com.betterfly.domain;


import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * A Processus.
 */
@Entity
@Table(name = "processus")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "processus")
public class Processus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "processus")
    private String processus;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "version")
    private Integer version;

    @Column(name = "finalite")
    private String finalite;
    
    @Column(name = "vigueur")
    private Boolean vigueur = true;
    

    @Lob
    @Column(name = "fiche")
    private byte[] fiche;

    @Column(name = "fiche_content_type")
    private String ficheContentType;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessus() {
        return processus;
    }

    public Processus processus(String processus) {
        this.processus = processus;
        return this;
    }

    public void setProcessus(String processus) {
        this.processus = processus;
    }

    public LocalDate getDate() {
        return date;
    }

    public Processus date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getVersion() {
        return version;
    }

    public Processus version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFinalite() {
        return finalite;
    }

    public Processus finalite(String finalite) {
        this.finalite = finalite;
        return this;
    }

    public void setFinalite(String finalite) {
        this.finalite = finalite;
    }

    public byte[] getFiche() {
        return fiche;
    }

    public Processus fiche(byte[] fiche) {
        this.fiche = fiche;
        return this;
    }

    public void setFiche(byte[] fiche) {
        this.fiche = fiche;
    }

    public String getFicheContentType() {
        return ficheContentType;
    }

    public Processus ficheContentType(String ficheContentType) {
        this.ficheContentType = ficheContentType;
        return this;
    }

    public void setFicheContentType(String ficheContentType) {
        this.ficheContentType = ficheContentType;
    }

    public String getType() {
        return type;
    }

    public Processus type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Processus)) {
            return false;
        }
        return id != null && id.equals(((Processus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Processus{" +
            "id=" + getId() +
            ", processus='" + getProcessus() + "'" +
            ", date='" + getDate() + "'" +
            ", version=" + getVersion() +
            ", finalite='" + getFinalite() + "'" +
            ", fiche='" + getFiche() + "'" +
            ", ficheContentType='" + getFicheContentType() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
