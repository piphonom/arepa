package org.piphonom.arepa.dao.dataset;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by piphonom
 */
@Entity
@Table(name = "Certificate")
public class Certificate {
    private Integer idCertificate;
    private Long serialNumber;
    private byte[] value;
    private DeviceGroup deviceGroupRef;
    private Boolean revoked;

    public Certificate() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCertificate")
    public Integer getIdCertificate() {
        return idCertificate;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    @Column(name = "value", columnDefinition = "BLOB")
    public byte[] getValue() {
        return value;
    }

    @ManyToOne
    @JoinColumn(name="deviceGroupRef")
    public DeviceGroup getDeviceGroupRef() {
        return deviceGroupRef;
    }

    @Column(name = "isRevoked", columnDefinition = "TINYINT", nullable = true)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    public Boolean getRevoked() {
        return revoked == null ? false : revoked;
    }

    public void setIdCertificate(Integer idCertificate) {
        this.idCertificate = idCertificate;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public void setDeviceGroupRef(DeviceGroup deviceGroupRef) {
        this.deviceGroupRef = deviceGroupRef;
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }
}
