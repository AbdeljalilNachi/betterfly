package com.betterfly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.betterfly.domain.enumeration.Situation;

import com.betterfly.domain.enumeration.EnumFive;

/**
 * A AnalyseSST.
 */
@Entity
@Table(name = "analyse_sst")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "analysesst")
public class AnalyseSST implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "processus")
    private String processus;

    @Column(name = "buisness_unit")
    private String buisnessUnit;

    @Column(name = "unite_travail")
    private String uniteTravail;

    @Column(name = "danger")
    private String danger;

    @Column(name = "risque")
    private String risque;

    @Column(name = "competence")
    private String competence;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation")
    private Situation situation;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequence")
    private EnumFive frequence;

    @Enumerated(EnumType.STRING)
    @Column(name = "duree_exposition")
    private EnumFive dureeExposition;

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

    public AnalyseSST date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getProcessus() {
        return processus;
    }

    public AnalyseSST processus(String processus) {
        this.processus = processus;
        return this;
    }

    public void setProcessus(String processus) {
        this.processus = processus;
    }

    public String getBuisnessUnit() {
        return buisnessUnit;
    }

    public AnalyseSST buisnessUnit(String buisnessUnit) {
        this.buisnessUnit = buisnessUnit;
        return this;
    }

    public void setBuisnessUnit(String buisnessUnit) {
        this.buisnessUnit = buisnessUnit;
    }

    public String getUniteTravail() {
        return uniteTravail;
    }

    public AnalyseSST uniteTravail(String uniteTravail) {
        this.uniteTravail = uniteTravail;
        return this;
    }

    public void setUniteTravail(String uniteTravail) {
        this.uniteTravail = uniteTravail;
    }

    public String getDanger() {
        return danger;
    }

    public AnalyseSST danger(String danger) {
        this.danger = danger;
        return this;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getRisque() {
        return risque;
    }

    public AnalyseSST risque(String risque) {
        this.risque = risque;
        return this;
    }

    public void setRisque(String risque) {
        this.risque = risque;
    }

    public String getCompetence() {
        return competence;
    }

    public AnalyseSST competence(String competence) {
        this.competence = competence;
        return this;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public Situation getSituation() {
        return situation;
    }

    public AnalyseSST situation(Situation situation) {
        this.situation = situation;
        return this;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public EnumFive getFrequence() {
        return frequence;
    }

    public AnalyseSST frequence(EnumFive frequence) {
        this.frequence = frequence;
        return this;
    }

    public void setFrequence(EnumFive frequence) {
        this.frequence = frequence;
    }

    public EnumFive getDureeExposition() {
        return dureeExposition;
    }

    public AnalyseSST dureeExposition(EnumFive dureeExposition) {
        this.dureeExposition = dureeExposition;
        return this;
    }

    public void setDureeExposition(EnumFive dureeExposition) {
        this.dureeExposition = dureeExposition;
    }

    public EnumFive getCoefficientMaitrise() {
        return coefficientMaitrise;
    }

    public AnalyseSST coefficientMaitrise(EnumFive coefficientMaitrise) {
        this.coefficientMaitrise = coefficientMaitrise;
        return this;
    }

    public void setCoefficientMaitrise(EnumFive coefficientMaitrise) {
        this.coefficientMaitrise = coefficientMaitrise;
    }

    public EnumFive getGravite() {
        return gravite;
    }

    public AnalyseSST gravite(EnumFive gravite) {
        this.gravite = gravite;
        return this;
    }

    public void setGravite(EnumFive gravite) {
        this.gravite = gravite;
    }

    public Integer getCriticite() {
        return criticite;
    }

    public AnalyseSST criticite(Integer criticite) {
        this.criticite = criticite;
        return this;
    }

    public void setCriticite(Integer criticite) {
        this.criticite = criticite;
    }

    public String getMaitriseExistante() {
        return maitriseExistante;
    }

    public AnalyseSST maitriseExistante(String maitriseExistante) {
        this.maitriseExistante = maitriseExistante;
        return this;
    }

    public void setMaitriseExistante(String maitriseExistante) {
        this.maitriseExistante = maitriseExistante;
    }

    public String getOrigine() {
        return origine;
    }

    public AnalyseSST origine(String origine) {
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
        if (!(o instanceof AnalyseSST)) {
            return false;
        }
        return id != null && id.equals(((AnalyseSST) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyseSST{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", processus='" + getProcessus() + "'" +
            ", buisnessUnit='" + getBuisnessUnit() + "'" +
            ", uniteTravail='" + getUniteTravail() + "'" +
            ", danger='" + getDanger() + "'" +
            ", risque='" + getRisque() + "'" +
            ", competence='" + getCompetence() + "'" +
            ", situation='" + getSituation() + "'" +
            ", frequence='" + getFrequence() + "'" +
            ", dureeExposition='" + getDureeExposition() + "'" +
            ", coefficientMaitrise='" + getCoefficientMaitrise() + "'" +
            ", gravite='" + getGravite() + "'" +
            ", criticite=" + getCriticite() +
            ", maitriseExistante='" + getMaitriseExistante() + "'" +
            ", origine='" + getOrigine() + "'" +
            "}";
    }
}
