package com.betterfly.domain;


import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.betterfly.domain.enumeration.TypeProcessus;

/**
 * A ProcessusSMI.
 */
@Entity
@Table(name = "processus_smi")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "processussmi")
public class ProcessusSMI implements Serializable {

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

    @Column(name = "pilote")
    private String pilote;

    @Column(name = "finalite")
    private String finalite;

    @Lob
    @Column(name = "fiche_processus")
    private byte[] ficheProcessus;

    @Column(name = "fiche_processus_content_type")
    private String ficheProcessusContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeProcessus type;

    @Column(name = "vigueur")
    private Boolean vigueur;

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

    public ProcessusSMI processus(String processus) {
        this.processus = processus;
        return this;
    }

    public void setProcessus(String processus) {
        this.processus = processus;
    }

    public LocalDate getDate() {
        return date;
    }

    public ProcessusSMI date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getVersion() {
        return version;
    }

    public ProcessusSMI version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPilote() {
        return pilote;
    }

    public ProcessusSMI pilote(String pilote) {
        this.pilote = pilote;
        return this;
    }

    public void setPilote(String pilote) {
        this.pilote = pilote;
    }

    public String getFinalite() {
        return finalite;
    }

    public ProcessusSMI finalite(String finalite) {
        this.finalite = finalite;
        return this;
    }

    public void setFinalite(String finalite) {
        this.finalite = finalite;
    }

    public byte[] getFicheProcessus() {
        return ficheProcessus;
    }

    public ProcessusSMI ficheProcessus(byte[] ficheProcessus) {
        this.ficheProcessus = ficheProcessus;
        return this;
    }

    public void setFicheProcessus(byte[] ficheProcessus) {
        this.ficheProcessus = ficheProcessus;
    }

    public String getFicheProcessusContentType() {
        return ficheProcessusContentType;
    }

    public ProcessusSMI ficheProcessusContentType(String ficheProcessusContentType) {
        this.ficheProcessusContentType = ficheProcessusContentType;
        return this;
    }

    public void setFicheProcessusContentType(String ficheProcessusContentType) {
        this.ficheProcessusContentType = ficheProcessusContentType;
    }

    public TypeProcessus getType() {
        return type;
    }

    public ProcessusSMI type(TypeProcessus type) {
        this.type = type;
        return this;
    }

    public void setType(TypeProcessus type) {
        this.type = type;
    }

    public Boolean isVigueur() {
        return vigueur;
    }

    public ProcessusSMI vigueur(Boolean vigueur) {
        this.vigueur = vigueur;
        return this;
    }

    public void setVigueur(Boolean vigueur) {
        this.vigueur = vigueur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessusSMI)) {
            return false;
        }
        return id != null && id.equals(((ProcessusSMI) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessusSMI{" +
            "id=" + getId() +
            ", processus='" + getProcessus() + "'" +
            ", date='" + getDate() + "'" +
            ", version=" + getVersion() +
            ", pilote='" + getPilote() + "'" +
            ", finalite='" + getFinalite() + "'" +
            ", ficheProcessus='" + getFicheProcessus() + "'" +
            ", ficheProcessusContentType='" + getFicheProcessusContentType() + "'" +
            ", type='" + getType() + "'" +
            ", vigueur='" + isVigueur() + "'" +
            "}";
    }
}
