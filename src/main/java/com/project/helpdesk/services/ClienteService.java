package com.project.helpdesk.services;

import com.project.helpdesk.domain.Pessoa;
import com.project.helpdesk.domain.Cliente;
import com.project.helpdesk.domain.dtos.ClienteDTO;
import com.project.helpdesk.repositories.PessoaRepository;
import com.project.helpdesk.repositories.ClienteRepository;
import com.project.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.project.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;

    public Cliente findById(Integer id){
        Optional<Cliente> cli = repository.findById(id);
        /*return cli.orElse(null);*/
        return cli.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO ClienteDTO) {
        ClienteDTO.setId(null);
        validaPorCpfEEmail(ClienteDTO);
        return repository.save(new Cliente(ClienteDTO));
    }

    private void validaPorCpfEEmail(ClienteDTO ClienteDTO) {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf(ClienteDTO.getCpf());
        if(pessoa.isPresent() && !Objects.equals(pessoa.get().getId(), ClienteDTO.getId())){
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        pessoa = pessoaRepository.findByEmail(ClienteDTO.getEmail());
        if(pessoa.isPresent() && !Objects.equals(pessoa.get().getId(), ClienteDTO.getId())){
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }

    }


    public Cliente update(Integer id, ClienteDTO ClienteDTO) {
        ClienteDTO.setId(id);
        Cliente oldCli = findById(id);
        validaPorCpfEEmail(ClienteDTO);
        oldCli = new Cliente(ClienteDTO);
        return repository.save(oldCli);
    }

    public void delete(Integer id) {
        Cliente cli = findById(id);
        if(!cli.getChamados().isEmpty()){
            throw new DataIntegrityViolationException("O cliente possui ordens de servicço e não pode ser deletado!");
        }
        repository.delete(cli);
    }
}
