package com.gft.mvcproject.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Starter {
    @Id
    @Column(name = "starter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo obrigatório!")
    private String nome;

    @NotBlank(message = "Campo obrigatório!")
    @Size(min = 4, max = 4, message = "Insira as quatro letras!")
    private String quatroLetras;

    @OneToOne
    private ProgramaStart programaStart;

    @Column(nullable = true)
    private String photos;

    public Starter() {

    }

    public Starter(String nome, String quatroLetras, ProgramaStart programaStart) {
        this.nome = nome;
        this.quatroLetras = quatroLetras;
        this.programaStart = programaStart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQuatroLetras() {
        return quatroLetras;
    }

    public void setQuatroLetras(String quatroLetras) {
        this.quatroLetras = quatroLetras;
    }

    public ProgramaStart getProgramaStart() {
        return programaStart;
    }

    public void setProgramaStart(ProgramaStart programaStart) {
        this.programaStart = programaStart;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }
}
