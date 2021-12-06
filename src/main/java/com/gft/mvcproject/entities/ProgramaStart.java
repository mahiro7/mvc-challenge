package com.gft.mvcproject.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class ProgramaStart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo obrigatório!")
    private String turma;

    @NotNull(message = "Campo obrigatório")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inicio;

    @NotNull(message = "Campo obrigatório")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date termino;

    public ProgramaStart() {

    }

    public ProgramaStart(String turma, Date inicio, Date termino) {
        this.turma = turma;
        this.inicio = inicio;
        this.termino = termino;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getTermino() {
        return termino;
    }

    public void setTermino(Date termino) {
        this.termino = termino;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
