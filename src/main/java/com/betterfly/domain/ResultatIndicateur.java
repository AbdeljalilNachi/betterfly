package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.betterfly.domain.enumeration.Mois;

/**
 * A ResultatIndicateur.
 */
@Entity
@Table(name = "resultat_indicateur")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "resultatindicateur")
public class ResultatIndicateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mois")
    private Mois mois;

    @Column(name = "cible")
    private Float cible;

    @Column(name = "resultat")
    private Float resultat;

    @ManyToOne
    @JsonIgnoreProperties(value = "resultats", allowSetters = true)
    private ResultIndicateurs resultIndicateurs;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mois getMois() {
        return mois;
    }

    public ResultatIndicateur mois(Mois mois) {
        this.mois = mois;
        return this;
    }

    public void setMois(Mois mois) {
        this.mois = mois;
    }

    public Float getCible() {
        return cible;
    }

    public ResultatIndicateur cible(Float cible) {
        this.cible = cible;
        return this;
    }

    public void setCible(Float cible) {
        this.cible = cible;
    }

    public Float getResultat() {
        return resultat;
    }

    public ResultatIndicateur resultat(Float resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(Float resultat) {
        this.resultat = resultat;
    }

    public ResultIndicateurs getResultIndicateurs() {
        return resultIndicateurs;
    }

    public ResultatIndicateur resultIndicateurs(ResultIndicateurs resultIndicateurs) {
        this.resultIndicateurs = resultIndicateurs;
        return this;
    }

    public void setResultIndicateurs(ResultIndicateurs resultIndicateurs) {
        this.resultIndicateurs = resultIndicateurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultatIndicateur)) {
            return false;
        }
        return id != null && id.equals(((ResultatIndicateur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultatIndicateur{" +
            "id=" + getId() +
            ", mois='" + getMois() + "'" +
            ", cible=" + getCible() +
            ", resultat=" + getResultat() +
            "}";
    }
}
