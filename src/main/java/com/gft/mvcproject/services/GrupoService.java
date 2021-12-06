package com.gft.mvcproject.services;

import com.gft.mvcproject.entities.Grupo;
import com.gft.mvcproject.entities.User;
import com.gft.mvcproject.repositories.GrupoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {
    @Autowired
    private GrupoRepository grupoRepository;

    public Grupo saveGrupo(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public List<Grupo> listGrupos() {
        return grupoRepository.findAll();
    }

    public Grupo getGrupo(Long id) throws NotFoundException {
        Optional<Grupo> grupo = grupoRepository.findById(id);

        if (grupo.isEmpty()) {
            throw new NotFoundException("Grupo não encontrado!");
        }

        return grupo.get();
    }

    public void deleteGrupo(Long id) {
        grupoRepository.deleteById(id);
    }

    public boolean isUnique(User scrumMaster) {
        return grupoRepository.findByScrumMaster(scrumMaster) == null;
    }
}
