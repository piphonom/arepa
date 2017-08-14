package org.piphonom.arepa.dao.dataset;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by piphonom
 */
@Entity
@Table(name = "DeviceToken")
public class DeviceToken {
    private Integer idDeviceToken;
    private Timestamp creationTime;
    private DeviceGroup deviceGroupRef;

    public DeviceToken() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDeviceToken")
    public Integer getIdDeviceToken() {
        return idDeviceToken;
    }

    public void setIdDeviceToken(Integer idDeviceToken) {
        this.idDeviceToken = idDeviceToken;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    @ManyToOne
    @JoinColumn(name="deviceGroupRef")
    public DeviceGroup getDeviceGroupRef() {
        return deviceGroupRef;
    }

    public void setDeviceGroupRef(DeviceGroup deviceGroupRef) {
        this.deviceGroupRef = deviceGroupRef;
    }
}
