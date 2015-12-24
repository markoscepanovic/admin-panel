package com.logate.adminpanel.domain;

import com.logate.adminpanel.domain.enumeration.Satus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TestFeatures.
 */
@Entity
@Table(name = "test_features")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "testfeatures")
public class TestFeatures implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "validation_string_field", length = 10, nullable = false)
    private String validationStringField;

    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "validation_reg_exp_field")
    private String validationRegExpField;

    @NotNull
    @Lob
    @Column(name = "blob_field", nullable = false)
    private byte[] blobField;

    @Column(name = "blob_field_content_type", nullable = false)
    private String blobFieldContentType;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "enum_field", nullable = false)
    private Satus enumField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValidationStringField() {
        return validationStringField;
    }

    public void setValidationStringField(String validationStringField) {
        this.validationStringField = validationStringField;
    }

    public String getValidationRegExpField() {
        return validationRegExpField;
    }

    public void setValidationRegExpField(String validationRegExpField) {
        this.validationRegExpField = validationRegExpField;
    }

    public byte[] getBlobField() {
        return blobField;
    }

    public void setBlobField(byte[] blobField) {
        this.blobField = blobField;
    }

    public String getBlobFieldContentType() {
        return blobFieldContentType;
    }

    public void setBlobFieldContentType(String blobFieldContentType) {
        this.blobFieldContentType = blobFieldContentType;
    }

    public Satus getEnumField() {
        return enumField;
    }

    public void setEnumField(Satus enumField) {
        this.enumField = enumField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestFeatures testFeatures = (TestFeatures) o;
        return Objects.equals(id, testFeatures.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TestFeatures{" +
            "id=" + id +
            ", validationStringField='" + validationStringField + "'" +
            ", validationRegExpField='" + validationRegExpField + "'" +
            ", blobField='" + blobField + "'" +
            ", blobFieldContentType='" + blobFieldContentType + "'" +
            ", enumField='" + enumField + "'" +
            '}';
    }
}
