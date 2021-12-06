package com.gft.mvcproject.repositories;

import com.gft.mvcproject.entities.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {
}