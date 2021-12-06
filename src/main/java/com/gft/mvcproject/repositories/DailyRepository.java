package com.gft.mvcproject.repositories;

import com.gft.mvcproject.entities.Daily;
import com.gft.mvcproject.entities.Projeto;
import com.gft.mvcproject.entities.Starter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {
    @Query("SELECT d FROM Daily d WHERE d.starter = :starter AND d.data = :dia")
    public Projeto findByStarterNDia(@Param("starter") Starter starter, @Param("dia") Date dia);
}
