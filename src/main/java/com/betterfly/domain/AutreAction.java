package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A AutreAction.
 */
@Entity
@Table(name = "autre_action")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "autreaction")
public class AutreAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "origine_action")
    private String origineAction;

    @Column(name = "origine")
    private String origine;

    @ManyToOne
    @JsonIgnoreProperties(value = "autreActions", allowSetters = true)
    private Action action;

    @ManyToOne
    @JsonIgnoreProperties(value = "autreActions", allowSetters = true)
    private User delegue;

    @ManyToOne
    @JsonIgnoreProperties(value = "autreActions", allowSetters = true)
    private ProcessusSMI processus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigineAction() {
        return origineAction;
    }

    public AutreAction origineAction(String origineAction) {
        this.origineAction = origineAction;
        return this;
    }

    public void setOrigineAction(String origineAction) {
        this.origineAction = origineAction;
    }

    public String getOrigine() {
        return origine;
    }

    public AutreAction origine(String origine) {
        this.origine = origine;
        return this;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Action getAction() {
        return action;
    }

    public AutreAction action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public User getDelegue() {
        return delegue;
    }

    public AutreAction delegue(User user) {
        this.delegue = user;
        return this;
    }

    public void setDelegue(User user) {
        this.delegue = user;
    }

    public ProcessusSMI getProcessus() {
        return processus;
    }

    public AutreAction processus(ProcessusSMI processusSMI) {
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
        if (!(o instanceof AutreAction)) {
            return false;
        }
        return id != null && id.equals(((AutreAction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutreAction{" +
            "id=" + getId() +
            ", origineAction='" + getOrigineAction() + "'" +
            ", origine='" + getOrigine() + "'" +
            "}";
    }
}
