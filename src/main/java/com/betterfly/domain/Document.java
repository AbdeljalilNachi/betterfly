package com.betterfly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.betterfly.domain.enumeration.TypeDocument;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "document")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "intitule")
    private String intitule;

    @Column(name = "code")
    private String code;

    @Column(name = "version")
    private Integer version;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeDocument type;

    @Lob
    @Column(name = "piece_jointe")
    private byte[] pieceJointe;

    @Column(name = "piece_jointe_content_type")
    private String pieceJointeContentType;

    @Column(name = "en_application")
    private Boolean enApplication;

    @Column(name = "appouve")
    private Boolean appouve;

    @ManyToOne
    @JsonIgnoreProperties(value = "documents", allowSetters = true)
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

    public Document date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getIntitule() {
        return intitule;
    }

    public Document intitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getCode() {
        return code;
    }

    public Document code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getVersion() {
        return version;
    }

    public Document version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public TypeDocument getType() {
        return type;
    }

    public Document type(TypeDocument type) {
        this.type = type;
        return this;
    }

    public void setType(TypeDocument type) {
        this.type = type;
    }

    public byte[] getPieceJointe() {
        return pieceJointe;
    }

    public Document pieceJointe(byte[] pieceJointe) {
        this.pieceJointe = pieceJointe;
        return this;
    }

    public void setPieceJointe(byte[] pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public String getPieceJointeContentType() {
        return pieceJointeContentType;
    }

    public Document pieceJointeContentType(String pieceJointeContentType) {
        this.pieceJointeContentType = pieceJointeContentType;
        return this;
    }

    public void setPieceJointeContentType(String pieceJointeContentType) {
        this.pieceJointeContentType = pieceJointeContentType;
    }

    public Boolean isEnApplication() {
        return enApplication;
    }

    public Document enApplication(Boolean enApplication) {
        this.enApplication = enApplication;
        return this;
    }

    public void setEnApplication(Boolean enApplication) {
        this.enApplication = enApplication;
    }

    public Boolean isAppouve() {
        return appouve;
    }

    public Document appouve(Boolean appouve) {
        this.appouve = appouve;
        return this;
    }

    public void setAppouve(Boolean appouve) {
        this.appouve = appouve;
    }

    public ProcessusSMI getProcessus() {
        return processus;
    }

    public Document processus(ProcessusSMI processusSMI) {
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
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", intitule='" + getIntitule() + "'" +
            ", code='" + getCode() + "'" +
            ", version=" + getVersion() +
            ", type='" + getType() + "'" +
            ", pieceJointe='" + getPieceJointe() + "'" +
            ", pieceJointeContentType='" + getPieceJointeContentType() + "'" +
            ", enApplication='" + isEnApplication() + "'" +
            ", appouve='" + isAppouve() + "'" +
            "}";
    }
}
