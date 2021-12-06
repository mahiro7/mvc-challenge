package com.gft.mvcproject.services;

import com.gft.mvcproject.entities.Starter;
import com.gft.mvcproject.repositories.StarterRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StarterService {
    @Autowired
    private StarterRepository starterRepository;

    public Starter saveStarter(Starter starter) {
        return starterRepository.save(starter);
    }

    public List<Starter> listStarters() {
        return starterRepository.findAll();
    }

    public Starter getStarter(Long id) throws NotFoundException {
        Optional<Starter> starter = starterRepository.findById(id);

        if (starter.isEmpty()) {
            throw new NotFoundException("Starter não encontrado!");
        }

        return starter.get();
    }

    public void deleteStarter(Long id) {
        starterRepository.deleteById(id);
    }

    public boolean isUnique(String quatroLetras) {
        return starterRepository.getStarterByCode(quatroLetras) == null;
    }
}