package com.project.helpdesk.services;

import com.project.helpdesk.domain.Tecnico;
import com.project.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository repository;

    public Tecnico findById(Integer id){
        Optional<Tecnico> tec = repository.findById(id);
        return tec.orElse(null);
    }
}
