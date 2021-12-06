package com.gft.mvcproject.repositories;

import com.gft.mvcproject.entities.ProgramaStart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProgramaStartRepository extends JpaRepository<ProgramaStart, Long> {
    @Query("SELECT p FROM ProgramaStart p WHERE p.turma = :turma")
    public ProgramaStart getProgramaStartByName(@Param("turma") String turma);
}
