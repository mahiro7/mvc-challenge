package com.gft.mvcproject.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Tecnologia tecnologia;

    @OneToMany
    private List<Starter> starters;

    @OneToOne
    private User scrumMaster;

    public Grupo() {

    }

    public Grupo(Tecnologia tecnologia, List<Starter> starters, User scrumMaster) {
        this.tecnologia = tecnologia;
        this.starters = starters;
        this.scrumMaster = scrumMaster;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tecnologia getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(Tecnologia tecnologia) {
        this.tecnologia = tecnologia;
    }

    public List<Starter> getStarters() {
        return starters;
    }

    public void setStarters(List<Starter> starters) {
        this.starters = starters;
    }

    public User getScrumMaster() {
        return scrumMaster;
    }

    public void setScrumMaster(User scrumMaster) {
        this.scrumMaster = scrumMaster;
    }
}
