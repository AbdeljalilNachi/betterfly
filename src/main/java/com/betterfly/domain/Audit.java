package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.betterfly.domain.enumeration.TypeAudit;

import com.betterfly.domain.enumeration.Standard;

import com.betterfly.domain.enumeration.StatutAudit;

/**
 * A Audit.
 */
@Entity
@Table(name = "audit")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "audit")
public class Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_audit")
    private LocalDate dateAudit;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_audit")
    private TypeAudit typeAudit;

    @Column(name = "auditeur")
    private String auditeur;

    @Enumerated(EnumType.STRING)
    @Column(name = "standard")
    private Standard standard;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutAudit statut;

    @Column(name = "conclusion")
    private String conclusion;

    @OneToMany(mappedBy = "audit")
    private Set<ProcessusSMI> procs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "audits", allowSetters = true)
    private ProcessusSMI processus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAudit() {
        return dateAudit;
    }

    public Audit dateAudit(LocalDate dateAudit) {
        this.dateAudit = dateAudit;
        return this;
    }

    public void setDateAudit(LocalDate dateAudit) {
        this.dateAudit = dateAudit;
    }

    public TypeAudit getTypeAudit() {
        return typeAudit;
    }

    public Audit typeAudit(TypeAudit typeAudit) {
        this.typeAudit = typeAudit;
        return this;
    }

    public void setTypeAudit(TypeAudit typeAudit) {
        this.typeAudit = typeAudit;
    }

    public String getAuditeur() {
        return auditeur;
    }

    public Audit auditeur(String auditeur) {
        this.auditeur = auditeur;
        return this;
    }

    public void setAuditeur(String auditeur) {
        this.auditeur = auditeur;
    }

    public Standard getStandard() {
        return standard;
    }

    public Audit standard(Standard standard) {
        this.standard = standard;
        return this;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public StatutAudit getStatut() {
        return statut;
    }

    public Audit statut(StatutAudit statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(StatutAudit statut) {
        this.statut = statut;
    }

    public String getConclusion() {
        return conclusion;
    }

    public Audit conclusion(String conclusion) {
        this.conclusion = conclusion;
        return this;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public Set<ProcessusSMI> getProcs() {
        return procs;
    }

    public Audit procs(Set<ProcessusSMI> processusSMIS) {
        this.procs = processusSMIS;
        return this;
    }

    public Audit addProcs(ProcessusSMI processusSMI) {
        this.procs.add(processusSMI);
        processusSMI.setAudit(this);
        return this;
    }

    public Audit removeProcs(ProcessusSMI processusSMI) {
        this.procs.remove(processusSMI);
        processusSMI.setAudit(null);
        return this;
    }

    public void setProcs(Set<ProcessusSMI> processusSMIS) {
        this.procs = processusSMIS;
    }

    public ProcessusSMI getProcessus() {
        return processus;
    }

    public Audit processus(ProcessusSMI processusSMI) {
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
        if (!(o instanceof Audit)) {
            return false;
        }
        return id != null && id.equals(((Audit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Audit{" +
            "id=" + getId() +
            ", dateAudit='" + getDateAudit() + "'" +
            ", typeAudit='" + getTypeAudit() + "'" +
            ", auditeur='" + getAuditeur() + "'" +
            ", standard='" + getStandard() + "'" +
            ", statut='" + getStatut() + "'" +
            ", conclusion='" + getConclusion() + "'" +
            "}";
    }
}
