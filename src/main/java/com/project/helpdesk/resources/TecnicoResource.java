package com.project.helpdesk.resources;

import com.project.helpdesk.domain.Tecnico;
import com.project.helpdesk.domain.dtos.TecnicoDTO;
import com.project.helpdesk.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/tecnicos")
public class TecnicoResource {

    @Autowired
    private TecnicoService service;

    @GetMapping(value="/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
        Tecnico tec = this.service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(tec));
    }

    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll(){
        List<Tecnico> list = service.findAll();
        //List<TecnicoDTO> listDTO = list.stream().map(tec -> new TecnicoDTO(tec)).collect(Collectors.toList());
        List<TecnicoDTO> listDTO = list.stream().map(TecnicoDTO::new).toList();
        return ResponseEntity.ok().body(listDTO);
    }

}
