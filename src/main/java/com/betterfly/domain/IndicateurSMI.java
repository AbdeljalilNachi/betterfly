package com.betterfly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A IndicateurSMI.
 */
@Entity
@Table(name = "indicateur_smi")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "indicateursmi")
public class IndicateurSMI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "processus")
    private String processus;

    @Column(name = "date_identification")
    private LocalDate dateIdentification;

    @Column(name = "indicateur")
    private String indicateur;

    @Column(name = "formule_calcul")
    private String formuleCalcul;

    @Column(name = "cible")
    private Float cible;

    @Column(name = "seuil_tolerance")
    private Float seuilTolerance;

    @Column(name = "unite")
    private String unite;

    @Column(name = "periodicite")
    private String periodicite;

    @Column(name = "responsable_calcul")
    private String responsableCalcul;

    @Column(name = "observations")
    private String observations;

    @Column(name = "vigueur")
    private Boolean vigueur;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "observation")
    private String observation;

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

    public IndicateurSMI processus(String processus) {
        this.processus = processus;
        return this;
    }

    public void setProcessus(String processus) {
        this.processus = processus;
    }

    public LocalDate getDateIdentification() {
        return dateIdentification;
    }

    public IndicateurSMI dateIdentification(LocalDate dateIdentification) {
        this.dateIdentification = dateIdentification;
        return this;
    }

    public void setDateIdentification(LocalDate dateIdentification) {
        this.dateIdentification = dateIdentification;
    }

    public String getIndicateur() {
        return indicateur;
    }

    public IndicateurSMI indicateur(String indicateur) {
        this.indicateur = indicateur;
        return this;
    }

    public void setIndicateur(String indicateur) {
        this.indicateur = indicateur;
    }

    public String getFormuleCalcul() {
        return formuleCalcul;
    }

    public IndicateurSMI formuleCalcul(String formuleCalcul) {
        this.formuleCalcul = formuleCalcul;
        return this;
    }

    public void setFormuleCalcul(String formuleCalcul) {
        this.formuleCalcul = formuleCalcul;
    }

    public Float getCible() {
        return cible;
    }

    public IndicateurSMI cible(Float cible) {
        this.cible = cible;
        return this;
    }

    public void setCible(Float cible) {
        this.cible = cible;
    }

    public Float getSeuilTolerance() {
        return seuilTolerance;
    }

    public IndicateurSMI seuilTolerance(Float seuilTolerance) {
        this.seuilTolerance = seuilTolerance;
        return this;
    }

    public void setSeuilTolerance(Float seuilTolerance) {
        this.seuilTolerance = seuilTolerance;
    }

    public String getUnite() {
        return unite;
    }

    public IndicateurSMI unite(String unite) {
        this.unite = unite;
        return this;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getPeriodicite() {
        return periodicite;
    }

    public IndicateurSMI periodicite(String periodicite) {
        this.periodicite = periodicite;
        return this;
    }

    public void setPeriodicite(String periodicite) {
        this.periodicite = periodicite;
    }

    public String getResponsableCalcul() {
        return responsableCalcul;
    }

    public IndicateurSMI responsableCalcul(String responsableCalcul) {
        this.responsableCalcul = responsableCalcul;
        return this;
    }

    public void setResponsableCalcul(String responsableCalcul) {
        this.responsableCalcul = responsableCalcul;
    }

    public String getObservations() {
        return observations;
    }

    public IndicateurSMI observations(String observations) {
        this.observations = observations;
        return this;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Boolean isVigueur() {
        return vigueur;
    }

    public IndicateurSMI vigueur(Boolean vigueur) {
        this.vigueur = vigueur;
        return this;
    }

    public void setVigueur(Boolean vigueur) {
        this.vigueur = vigueur;
    }

    public Integer getAnnee() {
        return annee;
    }

    public IndicateurSMI annee(Integer annee) {
        this.annee = annee;
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public String getObservation() {
        return observation;
    }

    public IndicateurSMI observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndicateurSMI)) {
            return false;
        }
        return id != null && id.equals(((IndicateurSMI) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndicateurSMI{" +
            "id=" + getId() +
            ", processus='" + getProcessus() + "'" +
            ", dateIdentification='" + getDateIdentification() + "'" +
            ", indicateur='" + getIndicateur() + "'" +
            ", formuleCalcul='" + getFormuleCalcul() + "'" +
            ", cible=" + getCible() +
            ", seuilTolerance=" + getSeuilTolerance() +
            ", unite='" + getUnite() + "'" +
            ", periodicite='" + getPeriodicite() + "'" +
            ", responsableCalcul='" + getResponsableCalcul() + "'" +
            ", observations='" + getObservations() + "'" +
            ", vigueur='" + isVigueur() + "'" +
            ", annee=" + getAnnee() +
            ", observation='" + getObservation() + "'" +
            "}";
    }
}
