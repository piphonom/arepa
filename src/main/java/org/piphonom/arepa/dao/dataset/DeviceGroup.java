package org.piphonom.arepa.dao.dataset;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private List<Device> devices = new ArrayList<>();

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Device",
               joinColumns = @JoinColumn(name = "deviceGroupRef"),
               inverseJoinColumns = @JoinColumn(name = "idDevice"))
    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
