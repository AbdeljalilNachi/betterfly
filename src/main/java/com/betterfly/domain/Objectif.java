package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Objectif.
 */
@Entity
@Table(name = "objectif")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "objectif")
public class Objectif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "axedelapolitiqueqse")
    private String axedelapolitiqueqse;

    @Column(name = "objectifqse")
    private String objectifqse;

    @Column(name = "origine")
    private String origine;

    @ManyToOne
    @JsonIgnoreProperties(value = "objectifs", allowSetters = true)
    private Action action;

    @ManyToOne
    @JsonIgnoreProperties(value = "objectifs", allowSetters = true)
    private User delegue;

    @ManyToOne
    @JsonIgnoreProperties(value = "objectifs", allowSetters = true)
    private ProcessusSMI processus;

    @ManyToOne
    @JsonIgnoreProperties(value = "objectifs", allowSetters = true)
    private IndicateurSMI indicateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAxedelapolitiqueqse() {
        return axedelapolitiqueqse;
    }

    public Objectif axedelapolitiqueqse(String axedelapolitiqueqse) {
        this.axedelapolitiqueqse = axedelapolitiqueqse;
        return this;
    }

    public void setAxedelapolitiqueqse(String axedelapolitiqueqse) {
        this.axedelapolitiqueqse = axedelapolitiqueqse;
    }

    public String getObjectifqse() {
        return objectifqse;
    }

    public Objectif objectifqse(String objectifqse) {
        this.objectifqse = objectifqse;
        return this;
    }

    public void setObjectifqse(String objectifqse) {
        this.objectifqse = objectifqse;
    }

    public String getOrigine() {
        return origine;
    }

    public Objectif origine(String origine) {
        this.origine = origine;
        return this;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Action getAction() {
        return action;
    }

    public Objectif action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public User getDelegue() {
        return delegue;
    }

    public Objectif delegue(User user) {
        this.delegue = user;
        return this;
    }

    public void setDelegue(User user) {
        this.delegue = user;
    }

    public ProcessusSMI getProcessus() {
        return processus;
    }

    public Objectif processus(ProcessusSMI processusSMI) {
        this.processus = processusSMI;
        return this;
    }

    public void setProcessus(ProcessusSMI processusSMI) {
        this.processus = processusSMI;
    }

    public IndicateurSMI getIndicateur() {
        return indicateur;
    }

    public Objectif indicateur(IndicateurSMI indicateurSMI) {
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
        if (!(o instanceof Objectif)) {
            return false;
        }
        return id != null && id.equals(((Objectif) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Objectif{" +
            "id=" + getId() +
            ", axedelapolitiqueqse='" + getAxedelapolitiqueqse() + "'" +
            ", objectifqse='" + getObjectifqse() + "'" +
            ", origine='" + getOrigine() + "'" +
            "}";
    }
}
