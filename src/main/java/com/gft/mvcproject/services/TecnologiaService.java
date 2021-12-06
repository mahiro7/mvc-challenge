package com.gft.mvcproject.services;

import com.gft.mvcproject.entities.Tecnologia;
import com.gft.mvcproject.repositories.TecnologiaRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnologiaService {
    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    public Tecnologia saveTecnologia(Tecnologia tecnologia) {
        return tecnologiaRepository.save(tecnologia);
    }

    public List<Tecnologia> listTecnologias() {
        return tecnologiaRepository.findAll();
    }

    public Tecnologia getTecnologia(Integer id) throws NotFoundException {
        Optional<Tecnologia> tecnologia = tecnologiaRepository.findById(id);

        if (tecnologia.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado!");
        }

        return tecnologia.get();
    }

    public void deleteTecnologia(Integer id) {
        tecnologiaRepository.deleteById(id);
    }

    public boolean isUnique(String nome) {
        return tecnologiaRepository.getTecnologiaByNome(nome) == null;
    }
}
