package com.openspection.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.jdo.annotations.Persistent;
import javax.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String password;

    public boolean isEnabled() {
        return isenabled;
    }

    public void setIsenabled(boolean isenabled) {
        this.isenabled = isenabled;
    }

    private boolean isenabled;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    
}
