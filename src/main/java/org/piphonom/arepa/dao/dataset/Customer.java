package org.piphonom.arepa.dao.dataset;

import javax.persistence.*;

/**
 * Created by piphonom
 */
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCustomer")
    private Integer idCustomer;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "token")
    private String token;
    @Column(name = "passhash")
    private String password;

    public Customer() {

    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
