package com.gft.mvcproject.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Modulo {
    @Id
    @GeneratedValue
    Integer id;

    @NotBlank(message = "Campo obrigatório!")
    String nome;

    @OneToMany
    List<Daily> dailies;

    @OneToMany
    List<Projeto> projetos;

    public Modulo() {

    }

    public Modulo(String nome, List<Daily> dailies, List<Projeto> projetos) {
        this.nome = nome;
        this.dailies = dailies;
        this.projetos = projetos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Daily> getDailies() {
        return dailies;
    }

    public void setDailies(List<Daily> dailies) {
        this.dailies = dailies;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }
}
