package com.betterfly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Constat.
 */
@Entity
@Table(name = "constat")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "constat")
public class Constat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "processus")
    private String processus;

    @Column(name = "audit")
    private String audit;

    @Column(name = "constat")
    private String constat;

    @Column(name = "type")
    private String type;

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

    public Constat processus(String processus) {
        this.processus = processus;
        return this;
    }

    public void setProcessus(String processus) {
        this.processus = processus;
    }

    public String getAudit() {
        return audit;
    }

    public Constat audit(String audit) {
        this.audit = audit;
        return this;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public String getConstat() {
        return constat;
    }

    public Constat constat(String constat) {
        this.constat = constat;
        return this;
    }

    public void setConstat(String constat) {
        this.constat = constat;
    }

    public String getType() {
        return type;
    }

    public Constat type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrigine() {
        return origine;
    }

    public Constat origine(String origine) {
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
        if (!(o instanceof Constat)) {
            return false;
        }
        return id != null && id.equals(((Constat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Constat{" +
            "id=" + getId() +
            ", processus='" + getProcessus() + "'" +
            ", audit='" + getAudit() + "'" +
            ", constat='" + getConstat() + "'" +
            ", type='" + getType() + "'" +
            ", origine='" + getOrigine() + "'" +
            "}";
    }
}
