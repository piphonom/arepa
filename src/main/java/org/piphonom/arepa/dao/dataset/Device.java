package org.piphonom.arepa.dao.dataset;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by piphonom
 */
@Entity
@Table(name = "Device")
public class Device {
    private Integer idDevice;
    private String name;
    private String pubId;
    private State state;
    private Timestamp creationTime;
    private DeviceGroup deviceGroupRef;
    private Certificate certificateRef;

    public enum State {
        CREATED,        /* created but not activated */
        ACTIVE,         /* activated */
        INACTIVE,       /* not activated in appropriate time */
        REMOVED         /* removed by group's owner */
    }

    public Device() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDevice")
    public Integer getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Integer idDevice) {
        this.idDevice = idDevice;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Enumerated(EnumType.STRING)
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
}
