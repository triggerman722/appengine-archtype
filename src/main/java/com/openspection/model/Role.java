package com.openspection.model;

import com.google.appengine.api.datastore.Key;

import java.util.List;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.*;

@Entity
public class Role {


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Long getPersonid() {
        return personid;
    }

    public void setPersonid(Long personid) {
        this.personid = personid;
    }

    private Long personid;

    private String name;





    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
