package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.betterfly.domain.enumeration.TypeRisque;

import com.betterfly.domain.enumeration.EnumFive;

import com.betterfly.domain.enumeration.Traitement;

/**
 * A Risque.
 */
@Entity
@Table(name = "risque")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "risque")
public class Risque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_identification")
    private LocalDate dateIdentification;

    @Column(name = "description")
    private String description;

    @Column(name = "cause_potentielle")
    private String causePotentielle;

    @Column(name = "effet_potentiel")
    private String effetPotentiel;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeRisque type;

    @Enumerated(EnumType.STRING)
    @Column(name = "gravite")
    private EnumFive gravite;

    @Enumerated(EnumType.STRING)
    @Column(name = "probabilite")
    private EnumFive probabilite;

    @Column(name = "criticite")
    private Integer criticite;

    @Enumerated(EnumType.STRING)
    @Column(name = "traitement")
    private Traitement traitement;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "origine")
    private String origine;

    @ManyToOne
    @JsonIgnoreProperties(value = "risques", allowSetters = true)
    private Action action;

    @ManyToOne
    @JsonIgnoreProperties(value = "risques", allowSetters = true)
    private User delegue;

    @ManyToOne
    @JsonIgnoreProperties(value = "risques", allowSetters = true)
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

    public Risque dateIdentification(LocalDate dateIdentification) {
        this.dateIdentification = dateIdentification;
        return this;
    }

    public void setDateIdentification(LocalDate dateIdentification) {
        this.dateIdentification = dateIdentification;
    }

    public String getDescription() {
        return description;
    }

    public Risque description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCausePotentielle() {
        return causePotentielle;
    }

    public Risque causePotentielle(String causePotentielle) {
        this.causePotentielle = causePotentielle;
        return this;
    }

    public void setCausePotentielle(String causePotentielle) {
        this.causePotentielle = causePotentielle;
    }

    public String getEffetPotentiel() {
        return effetPotentiel;
    }

    public Risque effetPotentiel(String effetPotentiel) {
        this.effetPotentiel = effetPotentiel;
        return this;
    }

    public void setEffetPotentiel(String effetPotentiel) {
        this.effetPotentiel = effetPotentiel;
    }

    public TypeRisque getType() {
        return type;
    }

    public Risque type(TypeRisque type) {
        this.type = type;
        return this;
    }

    public void setType(TypeRisque type) {
        this.type = type;
    }

    public EnumFive getGravite() {
        return gravite;
    }

    public Risque gravite(EnumFive gravite) {
        this.gravite = gravite;
        return this;
    }

    public void setGravite(EnumFive gravite) {
        this.gravite = gravite;
    }

    public EnumFive getProbabilite() {
        return probabilite;
    }

    public Risque probabilite(EnumFive probabilite) {
        this.probabilite = probabilite;
        return this;
    }

    public void setProbabilite(EnumFive probabilite) {
        this.probabilite = probabilite;
    }

    public Integer getCriticite() {
        return criticite;
    }

    public Risque criticite(Integer criticite) {
        this.criticite = criticite;
        return this;
    }

    public void setCriticite(Integer criticite) {
        this.criticite = criticite;
    }

    public Traitement getTraitement() {
        return traitement;
    }

    public Risque traitement(Traitement traitement) {
        this.traitement = traitement;
        return this;
    }

    public void setTraitement(Traitement traitement) {
        this.traitement = traitement;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Risque commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getOrigine() {
        return origine;
    }

    public Risque origine(String origine) {
        this.origine = origine;
        return this;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Action getAction() {
        return action;
    }

    public Risque action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public User getDelegue() {
        return delegue;
    }

    public Risque delegue(User user) {
        this.delegue = user;
        return this;
    }

    public void setDelegue(User user) {
        this.delegue = user;
    }

    public ProcessusSMI getProcessus() {
        return processus;
    }

    public Risque processus(ProcessusSMI processusSMI) {
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
        if (!(o instanceof Risque)) {
            return false;
        }
        return id != null && id.equals(((Risque) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Risque{" +
            "id=" + getId() +
            ", dateIdentification='" + getDateIdentification() + "'" +
            ", description='" + getDescription() + "'" +
            ", causePotentielle='" + getCausePotentielle() + "'" +
            ", effetPotentiel='" + getEffetPotentiel() + "'" +
            ", type='" + getType() + "'" +
            ", gravite='" + getGravite() + "'" +
            ", probabilite='" + getProbabilite() + "'" +
            ", criticite=" + getCriticite() +
            ", traitement='" + getTraitement() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", origine='" + getOrigine() + "'" +
            "}";
    }
}
