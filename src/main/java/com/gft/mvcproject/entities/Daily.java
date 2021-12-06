package com.gft.mvcproject.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Daily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "Campo obrigatório!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date data;

    String fazendo;
    String feito;
    String impedimentos;

    @NotNull(message = "Campo obrigatório!")
    boolean presente;

    @ManyToOne
    Starter starter;

    public Daily() {

    }

    public Daily(Date data, String fazendo, String feito, String impedimentos, boolean presente, Starter starter) {
        this.data = data;
        this.fazendo = fazendo;
        this.feito = feito;
        this.impedimentos = impedimentos;
        this.presente = presente;
        this.starter = starter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getFazendo() {
        return fazendo;
    }

    public void setFazendo(String fazendo) {
        this.fazendo = fazendo;
    }

    public String getFeito() {
        return feito;
    }

    public void setFeito(String feito) {
        this.feito = feito;
    }

    public String getImpedimentos() {
        return impedimentos;
    }

    public void setImpedimentos(String impedimentos) {
        this.impedimentos = impedimentos;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public Starter getStarter() {
        return starter;
    }

    public void setStarter(Starter starter) {
        this.starter = starter;
    }
}
