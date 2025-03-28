package com.project.helpdesk.resources;

import com.project.helpdesk.domain.Tecnico;
import com.project.helpdesk.domain.dtos.ResponseTecnicoDTO;
import com.project.helpdesk.domain.dtos.TecnicoDTO;
import com.project.helpdesk.services.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<List<ResponseTecnicoDTO>> findAll(){
        List<Tecnico> list = service.findAll();
        //List<ResponseTecnicoDTO> listDTO = list.stream().map(tec -> new ResponseTecnicoDTO(tec)).collect(Collectors.toList());
        List<ResponseTecnicoDTO> listDTO = list.stream().map(ResponseTecnicoDTO::new).toList();
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO tecnicoDTO){
        Tecnico newTec = service.create(tecnicoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newTec.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@Valid @PathVariable Integer id, @RequestBody TecnicoDTO tecnicoDTO){
        Tecnico newTec = service.update(id, tecnicoDTO);
        return ResponseEntity.ok().body(new TecnicoDTO(newTec));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }













}
