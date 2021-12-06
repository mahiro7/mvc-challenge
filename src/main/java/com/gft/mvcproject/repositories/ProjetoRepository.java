package com.gft.mvcproject.repositories;

import com.gft.mvcproject.entities.Projeto;
import com.gft.mvcproject.entities.Starter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    @Query("SELECT p FROM Projeto p WHERE p.starter = :starter AND p.etapa = :etapa")
    public Projeto findByStarterNEtapa(@Param("starter") Starter starter, @Param("etapa") String etapa);
}
