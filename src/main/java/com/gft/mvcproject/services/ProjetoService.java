package com.gft.mvcproject.services;

import com.gft.mvcproject.entities.Projeto;
import com.gft.mvcproject.entities.Starter;
import com.gft.mvcproject.repositories.ProjetoRepository;
import com.gft.mvcproject.repositories.ProjetoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    public Projeto saveProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public List<Projeto> listProjetos() {
        return projetoRepository.findAll();
    }

    public Projeto getProjeto(Long id) throws NotFoundException {
        Optional<Projeto> projeto = projetoRepository.findById(id);

        if (projeto.isEmpty()) {
            throw new NotFoundException("Projeto não encontrado!");
        }

        return projeto.get();
    }

    public void deleteProjeto(Long id) {
        projetoRepository.deleteById(id);
    }

    public boolean isUnique(Starter starter, String etapa) {
        return projetoRepository.findByStarterNEtapa(starter, etapa) == null;
    }
}
