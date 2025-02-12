package com.project.helpdesk.services;

import com.project.helpdesk.domain.Chamado;
import com.project.helpdesk.domain.Cliente;
import com.project.helpdesk.domain.Tecnico;
import com.project.helpdesk.domain.dtos.ChamadoDTO;
import com.project.helpdesk.domain.enums.Prioridade;
import com.project.helpdesk.domain.enums.Status;
import com.project.helpdesk.repositories.ChamadoRepository;
import com.project.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;

    public Chamado findById(Integer id){
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: "+ id));
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }

    public Chamado create(ChamadoDTO objDTO) {
        objDTO.setId(null);
        return repository.save(newChamado(objDTO));
    }

    public Chamado update(Integer id, ChamadoDTO objDTO) {
        objDTO.setId(id);
        Chamado oldObj = findById(id);
        oldObj = newChamado(objDTO);
        return repository.save(oldObj);

    }

    private Chamado newChamado(ChamadoDTO objDTO){

        Tecnico tecnico = tecnicoService.findById(objDTO.getTecnico());
        Cliente cliente = clienteService.findById(objDTO.getCliente());

        Chamado chamado = new Chamado();

        if(objDTO.getId() != null){
            chamado.setId(objDTO.getId());
        }

        if(objDTO.getStatus().equals(2)){
            chamado.setDataFechamento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(objDTO.getPrioridade()));
        chamado.setStatus(Status.toEnum(objDTO.getStatus()));
        chamado.setTitulo(objDTO.getTitulo());
        chamado.setObservacoes(objDTO.getObservacoes());

        return chamado;

    }

}
