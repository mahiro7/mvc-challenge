package com.gft.mvcproject.services;

import com.gft.mvcproject.entities.Cargo;
import com.gft.mvcproject.repositories.CargoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {
    @Autowired
    private CargoRepository cargoRepository;

    public Cargo saveCargo(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public List<Cargo> listCargos() {
        return cargoRepository.findAll();
    }

    public Cargo getCargo(Integer id) throws NotFoundException {
        Optional<Cargo> cargo = cargoRepository.findById(id);

        if (cargo.isEmpty()) {
            throw new NotFoundException("Cargo não encontrado!");
        }

        return cargo.get();
    }

    public void deleteCargo(Integer id) {
        cargoRepository.deleteById(id);
    }
}
