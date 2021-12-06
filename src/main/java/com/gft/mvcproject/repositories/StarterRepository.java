package com.gft.mvcproject.repositories;

import com.gft.mvcproject.entities.Starter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StarterRepository extends JpaRepository<Starter, Long> {
    @Query("SELECT s FROM Starter s WHERE s.quatroLetras = :quatroLetras")
    public Starter getStarterByCode(@Param("quatroLetras") String quatroLetras);

}
