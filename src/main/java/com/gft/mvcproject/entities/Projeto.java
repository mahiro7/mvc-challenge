package com.gft.mvcproject.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Min(value = 0, message = "Valor mínimo: 0")
    @Max(value = 100, message = "Valor máximo: 100")
    Integer nota;

    @NotBlank(message = "Campo obrigatório!")
    String etapa;

    @NotNull(message = "Campo obrigatório!")
    @ManyToOne
    Starter starter;

    public Projeto() {

    }

    public Projeto(Integer nota, String etapa, Starter starter) {
        this.nota = nota;
        this.etapa = etapa;
        this.starter = starter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public Starter getStarter() {
        return starter;
    }

    public void setStarter(Starter starter) {
        this.starter = starter;
    }
}
