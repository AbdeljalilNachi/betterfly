package com.betterfly.domain;


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

    @Column(name = "processus")
    private String processus;

    @Column(name = "axedelapolitiqueqse")
    private String axedelapolitiqueqse;

    @Column(name = "objectifqse")
    private String objectifqse;

    @Column(name = "indicateur")
    private String indicateur;

    @Column(name = "origine")
    private String origine;

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

    public Objectif processus(String processus) {
        this.processus = processus;
        return this;
    }

    public void setProcessus(String processus) {
        this.processus = processus;
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

    public String getIndicateur() {
        return indicateur;
    }

    public Objectif indicateur(String indicateur) {
        this.indicateur = indicateur;
        return this;
    }

    public void setIndicateur(String indicateur) {
        this.indicateur = indicateur;
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
            ", processus='" + getProcessus() + "'" +
            ", axedelapolitiqueqse='" + getAxedelapolitiqueqse() + "'" +
            ", objectifqse='" + getObjectifqse() + "'" +
            ", indicateur='" + getIndicateur() + "'" +
            ", origine='" + getOrigine() + "'" +
            "}";
    }
}
