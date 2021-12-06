package com.gft.mvcproject.repositories;

import com.gft.mvcproject.entities.Grupo;
import com.gft.mvcproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    @Query("SELECT g FROM Grupo g WHERE g.scrumMaster = :sm")
    public Grupo findByScrumMaster(@Param("sm") User sm);
}
