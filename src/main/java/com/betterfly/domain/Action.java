package com.betterfly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.betterfly.domain.enumeration.Statut;

import com.betterfly.domain.enumeration.Efficace;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "action")
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_planification")
    private LocalDate datePlanification;

    @Column(name = "action")
    private String action;

    @Column(name = "type")
    private String type;

    @Column(name = "delai")
    private LocalDate delai;

    @Column(name = "avancement")
    private String avancement;

    @Column(name = "realisee")
    private Boolean realisee;

    @Column(name = "critere_resultat")
    private String critereResultat;

    @Column(name = "ressources_necessaires")
    private String ressourcesNecessaires;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;

    @Enumerated(EnumType.STRING)
    @Column(name = "efficace")
    private Efficace efficace;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatePlanification() {
        return datePlanification;
    }

    public Action datePlanification(LocalDate datePlanification) {
        this.datePlanification = datePlanification;
        return this;
    }

    public void setDatePlanification(LocalDate datePlanification) {
        this.datePlanification = datePlanification;
    }

    public String getAction() {
        return action;
    }

    public Action action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public Action type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDelai() {
        return delai;
    }

    public Action delai(LocalDate delai) {
        this.delai = delai;
        return this;
    }

    public void setDelai(LocalDate delai) {
        this.delai = delai;
    }

    public String getAvancement() {
        return avancement;
    }

    public Action avancement(String avancement) {
        this.avancement = avancement;
        return this;
    }

    public void setAvancement(String avancement) {
        this.avancement = avancement;
    }

    public Boolean isRealisee() {
        return realisee;
    }

    public Action realisee(Boolean realisee) {
        this.realisee = realisee;
        return this;
    }

    public void setRealisee(Boolean realisee) {
        this.realisee = realisee;
    }

    public String getCritereResultat() {
        return critereResultat;
    }

    public Action critereResultat(String critereResultat) {
        this.critereResultat = critereResultat;
        return this;
    }

    public void setCritereResultat(String critereResultat) {
        this.critereResultat = critereResultat;
    }

    public String getRessourcesNecessaires() {
        return ressourcesNecessaires;
    }

    public Action ressourcesNecessaires(String ressourcesNecessaires) {
        this.ressourcesNecessaires = ressourcesNecessaires;
        return this;
    }

    public void setRessourcesNecessaires(String ressourcesNecessaires) {
        this.ressourcesNecessaires = ressourcesNecessaires;
    }

    public Statut getStatut() {
        return statut;
    }

    public Action statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Efficace getEfficace() {
        return efficace;
    }

    public Action efficace(Efficace efficace) {
        this.efficace = efficace;
        return this;
    }

    public void setEfficace(Efficace efficace) {
        this.efficace = efficace;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Action)) {
            return false;
        }
        return id != null && id.equals(((Action) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Action{" +
            "id=" + getId() +
            ", datePlanification='" + getDatePlanification() + "'" +
            ", action='" + getAction() + "'" +
            ", type='" + getType() + "'" +
            ", delai='" + getDelai() + "'" +
            ", avancement='" + getAvancement() + "'" +
            ", realisee='" + isRealisee() + "'" +
            ", critereResultat='" + getCritereResultat() + "'" +
            ", ressourcesNecessaires='" + getRessourcesNecessaires() + "'" +
            ", statut='" + getStatut() + "'" +
            ", efficace='" + getEfficace() + "'" +
            "}";
    }
}
