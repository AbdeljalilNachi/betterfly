package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.betterfly.domain.enumeration.TypeAnalyseSWOT;

/**
 * A AnalyseSWOT.
 */
@Entity
@Table(name = "analyse_swot")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "analyseswot")
public class AnalyseSWOT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_identification")
    private LocalDate dateIdentification;

    @Column(name = "description")
    private String description;

    @Column(name = "pilote")
    private String pilote;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeAnalyseSWOT type;

    @Column(name = "bu")
    private String bu;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "afficher")
    private Boolean afficher;

    @ManyToOne
    @JsonIgnoreProperties(value = "analyseSWOTS", allowSetters = true)
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

    public AnalyseSWOT dateIdentification(LocalDate dateIdentification) {
        this.dateIdentification = dateIdentification;
        return this;
    }

    public void setDateIdentification(LocalDate dateIdentification) {
        this.dateIdentification = dateIdentification;
    }

    public String getDescription() {
        return description;
    }

    public AnalyseSWOT description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPilote() {
        return pilote;
    }

    public AnalyseSWOT pilote(String pilote) {
        this.pilote = pilote;
        return this;
    }

    public void setPilote(String pilote) {
        this.pilote = pilote;
    }

    public TypeAnalyseSWOT getType() {
        return type;
    }

    public AnalyseSWOT type(TypeAnalyseSWOT type) {
        this.type = type;
        return this;
    }

    public void setType(TypeAnalyseSWOT type) {
        this.type = type;
    }

    public String getBu() {
        return bu;
    }

    public AnalyseSWOT bu(String bu) {
        this.bu = bu;
        return this;
    }

    public void setBu(String bu) {
        this.bu = bu;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public AnalyseSWOT commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Boolean isAfficher() {
        return afficher;
    }

    public AnalyseSWOT afficher(Boolean afficher) {
        this.afficher = afficher;
        return this;
    }

    public void setAfficher(Boolean afficher) {
        this.afficher = afficher;
    }

    public ProcessusSMI getProcessus() {
        return processus;
    }

    public AnalyseSWOT processus(ProcessusSMI processusSMI) {
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
        if (!(o instanceof AnalyseSWOT)) {
            return false;
        }
        return id != null && id.equals(((AnalyseSWOT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyseSWOT{" +
            "id=" + getId() +
            ", dateIdentification='" + getDateIdentification() + "'" +
            ", description='" + getDescription() + "'" +
            ", pilote='" + getPilote() + "'" +
            ", type='" + getType() + "'" +
            ", bu='" + getBu() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", afficher='" + isAfficher() + "'" +
            "}";
    }
}
