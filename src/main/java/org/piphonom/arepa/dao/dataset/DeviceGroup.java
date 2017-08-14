package org.piphonom.arepa.dao.dataset;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by piphonom
 */
@Entity
@Table(name = "DeviceGroup")
public class DeviceGroup {

    private Integer idDeviceGroup;
    private Customer ownerCustomerRef;
    private String name;

    private byte[] certificateCA;

    private Boolean isDeactivated;

    public DeviceGroup() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDeviceGroup")
    public Integer getIdDeviceGroup() {
        return idDeviceGroup;
    }

    public void setIdDeviceGroup(Integer idDeviceGroup) {
        this.idDeviceGroup = idDeviceGroup;
    }

    @ManyToOne
    @JoinColumn(name="ownerCustomerRef")
    public Customer getOwnerCustomerRef() {
        return ownerCustomerRef;
    }

    public void setOwnerCustomerRef(Customer ownerCustomerRef) {
        this.ownerCustomerRef = ownerCustomerRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getCertificateCA() {
        return certificateCA;
    }

    public void setCertificateCA(byte[] certificateCA) {
        this.certificateCA = certificateCA;
    }

    @Column(name = "isDeactivated", columnDefinition = "TINYINT", nullable = true)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    public Boolean getDeactivated() {
        return isDeactivated == null ? false : isDeactivated;
    }

    public void setDeactivated(Boolean deactivated) {
        isDeactivated = deactivated;
    }
}
