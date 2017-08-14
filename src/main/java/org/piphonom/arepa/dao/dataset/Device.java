package org.piphonom.arepa.dao.dataset;

import javax.persistence.*;

/**
 * Created by piphonom
 */
@Entity
@Table(name = "Device")
public class Device {
    private String idDevice;
    private String pubId;
    private DeviceGroup deviceGroupRef;
    private Certificate certificateRef;

    public Device() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDevice")
    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }


    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    @ManyToOne
    @JoinColumn(name="deviceGroupRef")
    public DeviceGroup getDeviceGroupRef() {
        return deviceGroupRef;
    }

    public void setDeviceGroupRef(DeviceGroup deviceGroupRef) {
        this.deviceGroupRef = deviceGroupRef;
    }

    @OneToOne
    @JoinColumn(name="certificateRef")
    public Certificate getCertificateRef() {
        return certificateRef;
    }

    public void setCertificateRef(Certificate certificateRef) {
        this.certificateRef = certificateRef;
    }
}
