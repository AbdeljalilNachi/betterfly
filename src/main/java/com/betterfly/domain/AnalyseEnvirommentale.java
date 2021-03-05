package com.betterfly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.betterfly.domain.enumeration.Situation;

import com.betterfly.domain.enumeration.EnumFive;

/**
 * A AnalyseEnvirommentale.
 */
@Entity
@Table(name = "analyse_envirommentale")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "analyseenvirommentale")
public class AnalyseEnvirommentale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "processus")
    private String processus;

    @Column(name = "business_unit")
    private String businessUnit;

    @Column(name = "activite")
    private String activite;

    @Column(name = "aspect_environnemental")
    private String aspectEnvironnemental;

    @Column(name = "impact_environnemental")
    private String impactEnvironnemental;

    @Column(name = "competences_requises")
    private String competencesRequises;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation")
    private Situation situation;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequence")
    private EnumFive frequence;

    @Enumerated(EnumType.STRING)
    @Column(name = "sensibilite_milieu")
    private EnumFive sensibiliteMilieu;

    @Enumerated(EnumType.STRING)
    @Column(name = "coefficient_maitrise")
    private EnumFive coefficientMaitrise;

    @Enumerated(EnumType.STRING)
    @Column(name = "gravite")
    private EnumFive gravite;

    @Column(name = "criticite")
    private Integer criticite;

    @Column(name = "maitrise_existante")
    private String maitriseExistante;

    @Column(name = "origine")
    private String origine;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public AnalyseEnvirommentale date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getProcessus() {
        return processus;
    }

    public AnalyseEnvirommentale processus(String processus) {
        this.processus = processus;
        return this;
    }

    public void setProcessus(String processus) {
        this.processus = processus;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public AnalyseEnvirommentale businessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
        return this;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getActivite() {
        return activite;
    }

    public AnalyseEnvirommentale activite(String activite) {
        this.activite = activite;
        return this;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getAspectEnvironnemental() {
        return aspectEnvironnemental;
    }

    public AnalyseEnvirommentale aspectEnvironnemental(String aspectEnvironnemental) {
        this.aspectEnvironnemental = aspectEnvironnemental;
        return this;
    }

    public void setAspectEnvironnemental(String aspectEnvironnemental) {
        this.aspectEnvironnemental = aspectEnvironnemental;
    }

    public String getImpactEnvironnemental() {
        return impactEnvironnemental;
    }

    public AnalyseEnvirommentale impactEnvironnemental(String impactEnvironnemental) {
        this.impactEnvironnemental = impactEnvironnemental;
        return this;
    }

    public void setImpactEnvironnemental(String impactEnvironnemental) {
        this.impactEnvironnemental = impactEnvironnemental;
    }

    public String getCompetencesRequises() {
        return competencesRequises;
    }

    public AnalyseEnvirommentale competencesRequises(String competencesRequises) {
        this.competencesRequises = competencesRequises;
        return this;
    }

    public void setCompetencesRequises(String competencesRequises) {
        this.competencesRequises = competencesRequises;
    }

    public Situation getSituation() {
        return situation;
    }

    public AnalyseEnvirommentale situation(Situation situation) {
        this.situation = situation;
        return this;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public EnumFive getFrequence() {
        return frequence;
    }

    public AnalyseEnvirommentale frequence(EnumFive frequence) {
        this.frequence = frequence;
        return this;
    }

    public void setFrequence(EnumFive frequence) {
        this.frequence = frequence;
    }

    public EnumFive getSensibiliteMilieu() {
        return sensibiliteMilieu;
    }

    public AnalyseEnvirommentale sensibiliteMilieu(EnumFive sensibiliteMilieu) {
        this.sensibiliteMilieu = sensibiliteMilieu;
        return this;
    }

    public void setSensibiliteMilieu(EnumFive sensibiliteMilieu) {
        this.sensibiliteMilieu = sensibiliteMilieu;
    }

    public EnumFive getCoefficientMaitrise() {
        return coefficientMaitrise;
    }

    public AnalyseEnvirommentale coefficientMaitrise(EnumFive coefficientMaitrise) {
        this.coefficientMaitrise = coefficientMaitrise;
        return this;
    }

    public void setCoefficientMaitrise(EnumFive coefficientMaitrise) {
        this.coefficientMaitrise = coefficientMaitrise;
    }

    public EnumFive getGravite() {
        return gravite;
    }

    public AnalyseEnvirommentale gravite(EnumFive gravite) {
        this.gravite = gravite;
        return this;
    }

    public void setGravite(EnumFive gravite) {
        this.gravite = gravite;
    }

    public Integer getCriticite() {
        return criticite;
    }

    public AnalyseEnvirommentale criticite(Integer criticite) {
        this.criticite = criticite;
        return this;
    }

    public void setCriticite(Integer criticite) {
        this.criticite = criticite;
    }

    public String getMaitriseExistante() {
        return maitriseExistante;
    }

    public AnalyseEnvirommentale maitriseExistante(String maitriseExistante) {
        this.maitriseExistante = maitriseExistante;
        return this;
    }

    public void setMaitriseExistante(String maitriseExistante) {
        this.maitriseExistante = maitriseExistante;
    }

    public String getOrigine() {
        return origine;
    }

    public AnalyseEnvirommentale origine(String origine) {
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
        if (!(o instanceof AnalyseEnvirommentale)) {
            return false;
        }
        return id != null && id.equals(((AnalyseEnvirommentale) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyseEnvirommentale{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", processus='" + getProcessus() + "'" +
            ", businessUnit='" + getBusinessUnit() + "'" +
            ", activite='" + getActivite() + "'" +
            ", aspectEnvironnemental='" + getAspectEnvironnemental() + "'" +
            ", impactEnvironnemental='" + getImpactEnvironnemental() + "'" +
            ", competencesRequises='" + getCompetencesRequises() + "'" +
            ", situation='" + getSituation() + "'" +
            ", frequence='" + getFrequence() + "'" +
            ", sensibiliteMilieu='" + getSensibiliteMilieu() + "'" +
            ", coefficientMaitrise='" + getCoefficientMaitrise() + "'" +
            ", gravite='" + getGravite() + "'" +
            ", criticite=" + getCriticite() +
            ", maitriseExistante='" + getMaitriseExistante() + "'" +
            ", origine='" + getOrigine() + "'" +
            "}";
    }
}
