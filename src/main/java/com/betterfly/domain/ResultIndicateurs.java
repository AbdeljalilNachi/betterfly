package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ResultIndicateurs.
 */
@Entity
@Table(name = "result_indicateurs")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "resultindicateurs")
public class ResultIndicateurs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "observation")
    private String observation;

    @OneToMany(mappedBy = "resultIndicateurs")
    private Set<ResultatIndicateur> resultats = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "resultIndicateurs", allowSetters = true)
    private IndicateurSMI indicateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnnee() {
        return annee;
    }

    public ResultIndicateurs annee(Integer annee) {
        this.annee = annee;
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public String getObservation() {
        return observation;
    }

    public ResultIndicateurs observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Set<ResultatIndicateur> getResultats() {
        return resultats;
    }

    public ResultIndicateurs resultats(Set<ResultatIndicateur> resultatIndicateurs) {
        this.resultats = resultatIndicateurs;
        return this;
    }

    public ResultIndicateurs addResultats(ResultatIndicateur resultatIndicateur) {
        this.resultats.add(resultatIndicateur);
        resultatIndicateur.setResultIndicateurs(this);
        return this;
    }

    public ResultIndicateurs removeResultats(ResultatIndicateur resultatIndicateur) {
        this.resultats.remove(resultatIndicateur);
        resultatIndicateur.setResultIndicateurs(null);
        return this;
    }

    public void setResultats(Set<ResultatIndicateur> resultatIndicateurs) {
        this.resultats = resultatIndicateurs;
    }

    public IndicateurSMI getIndicateur() {
        return indicateur;
    }

    public ResultIndicateurs indicateur(IndicateurSMI indicateurSMI) {
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
        if (!(o instanceof ResultIndicateurs)) {
            return false;
        }
        return id != null && id.equals(((ResultIndicateurs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultIndicateurs{" +
            "id=" + getId() +
            ", annee=" + getAnnee() +
            ", observation='" + getObservation() + "'" +
            "}";
    }
}
