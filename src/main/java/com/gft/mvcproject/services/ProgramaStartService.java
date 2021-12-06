package com.gft.mvcproject.services;

import com.gft.mvcproject.entities.ProgramaStart;
import com.gft.mvcproject.repositories.ProgramaStartRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramaStartService {
    @Autowired
    private ProgramaStartRepository programaStartRepository;


    public ProgramaStart saveProgramaStart(ProgramaStart turma) {
        return programaStartRepository.save(turma);
    }

    public List<ProgramaStart> listProgramaStart() {
        return programaStartRepository.findAll();
    }

    public ProgramaStart getProgramaStart(Long id) throws NotFoundException {
        Optional<ProgramaStart> turma = programaStartRepository.findById(id);

        if (turma.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado!");
        }

        return turma.get();
    }

    public void deleteProgramaStart(Long id) {
        programaStartRepository.deleteById(id);
    }

    public boolean isUnique(String turma) {
        return programaStartRepository.getProgramaStartByName(turma) == null;
    }
}
