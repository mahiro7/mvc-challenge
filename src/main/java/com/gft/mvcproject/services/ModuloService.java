package com.gft.mvcproject.services;

import com.gft.mvcproject.entities.Modulo;
import com.gft.mvcproject.repositories.ModuloRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuloService {
    @Autowired
    private ModuloRepository moduloRepository;

    public Modulo saveModulo(Modulo modulo) {
        return moduloRepository.save(modulo);
    }

    public List<Modulo> listDailies() {
        return moduloRepository.findAll();
    }

    public Modulo getModulo(Integer id) throws NotFoundException {
        Optional<Modulo> modulo = moduloRepository.findById(id);

        if (modulo.isEmpty()) {
            throw new NotFoundException("Modulo não encontrado!");
        }

        return modulo.get();
    }

    public void deleteModulo(Integer id) {
        moduloRepository.deleteById(id);
    }

    public boolean isUnique(String nome) {
        return moduloRepository.getByName(nome) == null;
    }
}
