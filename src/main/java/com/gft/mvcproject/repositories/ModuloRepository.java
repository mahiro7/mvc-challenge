package com.gft.mvcproject.repositories;

import com.gft.mvcproject.entities.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Integer> {

    @Query("SELECT m FROM Modulo m WHERE m.nome = :nome")
    public Modulo getByName(@Param("nome") String nome);
}
