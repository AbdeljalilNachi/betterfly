package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.betterfly.domain.enumeration.TypeConstatAudit;

/**
 * A ConstatAudit.
 */
@Entity
@Table(name = "constat_audit")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "constataudit")
public class ConstatAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeConstatAudit type;

    @Column(name = "constat")
    private String constat;

    @Column(name = "origine")
    private String origine;

    @ManyToOne
    @JsonIgnoreProperties(value = "constatAudits", allowSetters = true)
    private Action action;

    @ManyToOne
    @JsonIgnoreProperties(value = "constatAudits", allowSetters = true)
    private User delegue;

    @ManyToOne
    @JsonIgnoreProperties(value = "constatAudits", allowSetters = true)
    private ProcessusSMI processus;

    @ManyToOne
    @JsonIgnoreProperties(value = "constatAudits", allowSetters = true)
    private Audit audit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeConstatAudit getType() {
        return type;
    }

    public ConstatAudit type(TypeConstatAudit type) {
        this.type = type;
        return this;
    }

    public void setType(TypeConstatAudit type) {
        this.type = type;
    }

    public String getConstat() {
        return constat;
    }

    public ConstatAudit constat(String constat) {
        this.constat = constat;
        return this;
    }

    public void setConstat(String constat) {
        this.constat = constat;
    }

    public String getOrigine() {
        return origine;
    }

    public ConstatAudit origine(String origine) {
        this.origine = origine;
        return this;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Action getAction() {
        return action;
    }

    public ConstatAudit action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public User getDelegue() {
        return delegue;
    }

    public ConstatAudit delegue(User user) {
        this.delegue = user;
        return this;
    }

    public void setDelegue(User user) {
        this.delegue = user;
    }

    public ProcessusSMI getProcessus() {
        return processus;
    }

    public ConstatAudit processus(ProcessusSMI processusSMI) {
        this.processus = processusSMI;
        return this;
    }

    public void setProcessus(ProcessusSMI processusSMI) {
        this.processus = processusSMI;
    }

    public Audit getAudit() {
        return audit;
    }

    public ConstatAudit audit(Audit audit) {
        this.audit = audit;
        return this;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConstatAudit)) {
            return false;
        }
        return id != null && id.equals(((ConstatAudit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConstatAudit{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", constat='" + getConstat() + "'" +
            ", origine='" + getOrigine() + "'" +
            "}";
    }
}
