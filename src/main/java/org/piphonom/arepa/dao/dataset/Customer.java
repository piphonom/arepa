package org.piphonom.arepa.dao.dataset;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piphonom
 */
@Entity
@Table(name = "Customer")
public class Customer {

    private Integer idCustomer;
    private String email;
    private String name;
    private String token;
    private String password;

    private List<DeviceGroup> groups = new ArrayList<>();

    public Customer() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCustomer")
    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "passhash")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "DeviceGroup",
            joinColumns = @JoinColumn(name = "ownerCustomerRef"),
            inverseJoinColumns = @JoinColumn(name = "idDeviceGroup")
    )
    public List<DeviceGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<DeviceGroup> groups) {
        this.groups = groups;
    }
}
