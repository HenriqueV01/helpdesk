package com.project.helpdesk.resources;

import com.project.helpdesk.domain.Cliente;
import com.project.helpdesk.domain.dtos.ClienteDTO;
import com.project.helpdesk.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping(value="/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){
        Cliente tec = this.service.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(tec));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll(){
        List<Cliente> list = service.findAll();
        //List<ClienteDTO> listDTO = list.stream().map(tec -> new ClienteDTO(tec)).collect(Collectors.toList());
        List<ClienteDTO> listDTO = list.stream().map(ClienteDTO::new).toList();
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO clienteDTO){
        Cliente newTec = service.create(clienteDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newTec.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@Valid @PathVariable Integer id, @RequestBody ClienteDTO clienteDTO){
        Cliente newTec = service.update(id, clienteDTO);
        return ResponseEntity.ok().body(new ClienteDTO(newTec));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }













}
