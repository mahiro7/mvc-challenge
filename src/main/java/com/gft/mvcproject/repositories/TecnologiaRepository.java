package com.gft.mvcproject.repositories;

import com.gft.mvcproject.entities.Tecnologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TecnologiaRepository extends JpaRepository<Tecnologia, Integer> {
    @Query("SELECT t FROM Tecnologia t WHERE t.nome = :nome")
    public Tecnologia getTecnologiaByNome(@Param("nome") String nome);
}
