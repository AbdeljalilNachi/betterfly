package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Reclamation.
 */
@Entity
@Table(name = "reclamation")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "reclamation")
public class Reclamation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @Column(name = "justifiee")
    private Boolean justifiee;

    @Column(name = "client")
    private String client;

    @Lob
    @Column(name = "piecejointe")
    private byte[] piecejointe;

    @Column(name = "piecejointe_content_type")
    private String piecejointeContentType;

    @Column(name = "origine")
    private String origine;

    @ManyToOne
    @JsonIgnoreProperties(value = "reclamations", allowSetters = true)
    private Action action;

    @ManyToOne
    @JsonIgnoreProperties(value = "reclamations", allowSetters = true)
    private User delegue;

    @ManyToOne
    @JsonIgnoreProperties(value = "reclamations", allowSetters = true)
    private ProcessusSMI processus;

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

    public Reclamation date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public Reclamation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isJustifiee() {
        return justifiee;
    }

    public Reclamation justifiee(Boolean justifiee) {
        this.justifiee = justifiee;
        return this;
    }

    public void setJustifiee(Boolean justifiee) {
        this.justifiee = justifiee;
    }

    public String getClient() {
        return client;
    }

    public Reclamation client(String client) {
        this.client = client;
        return this;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public byte[] getPiecejointe() {
        return piecejointe;
    }

    public Reclamation piecejointe(byte[] piecejointe) {
        this.piecejointe = piecejointe;
        return this;
    }

    public void setPiecejointe(byte[] piecejointe) {
        this.piecejointe = piecejointe;
    }

    public String getPiecejointeContentType() {
        return piecejointeContentType;
    }

    public Reclamation piecejointeContentType(String piecejointeContentType) {
        this.piecejointeContentType = piecejointeContentType;
        return this;
    }

    public void setPiecejointeContentType(String piecejointeContentType) {
        this.piecejointeContentType = piecejointeContentType;
    }

    public String getOrigine() {
        return origine;
    }

    public Reclamation origine(String origine) {
        this.origine = origine;
        return this;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Action getAction() {
        return action;
    }

    public Reclamation action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public User getDelegue() {
        return delegue;
    }

    public Reclamation delegue(User user) {
        this.delegue = user;
        return this;
    }

    public void setDelegue(User user) {
        this.delegue = user;
    }

    public ProcessusSMI getProcessus() {
        return processus;
    }

    public Reclamation processus(ProcessusSMI processusSMI) {
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
        if (!(o instanceof Reclamation)) {
            return false;
        }
        return id != null && id.equals(((Reclamation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reclamation{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", justifiee='" + isJustifiee() + "'" +
            ", client='" + getClient() + "'" +
            ", piecejointe='" + getPiecejointe() + "'" +
            ", piecejointeContentType='" + getPiecejointeContentType() + "'" +
            ", origine='" + getOrigine() + "'" +
            "}";
    }
}
