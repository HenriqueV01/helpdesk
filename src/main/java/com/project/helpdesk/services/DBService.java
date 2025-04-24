package com.project.helpdesk.services;

import com.project.helpdesk.domain.Chamado;
import com.project.helpdesk.domain.Cliente;
import com.project.helpdesk.domain.Tecnico;
import com.project.helpdesk.domain.enums.Perfil;
import com.project.helpdesk.domain.enums.Prioridade;
import com.project.helpdesk.domain.enums.Status;
import com.project.helpdesk.repositories.ChamadoRepository;
import com.project.helpdesk.repositories.ClienteRepository;
import com.project.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService {

    /*@Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;*/ //<-Deu erro de injeção de dependência nesse atributo na iniciação do projeto,
    // teve que injetar de forma direta, sem Autowired.

    private final TecnicoRepository tecnicoRepository;
    private final ClienteRepository clienteRepository;
    private final ChamadoRepository chamadoRepository;
    private final PasswordEncoder encoder;

    public DBService(TecnicoRepository tecnicoRepository, ClienteRepository clienteRepository,
                     ChamadoRepository chamadoRepository, PasswordEncoder encoder) {
        this.tecnicoRepository = tecnicoRepository;
        this.clienteRepository = clienteRepository;
        this.chamadoRepository = chamadoRepository;
        this.encoder = encoder;
    }

    public void instaciaDB(){
        Tecnico tec1 = new Tecnico(null, "Henrique", "827.582.000-66", "tec01@email.com", encoder.encode("123456"));
        tec1.addPerfil(Perfil.ADMIN);

        Cliente cli1 = new Cliente(null, "Maria", "001.788.260-57", "cli1@email.com", encoder.encode("123456"));
        /*cli1.addPerfil(Perfil.CLIENTE);*/

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);

        tecnicoRepository.saveAll(List.of(tec1)); //Arrays.asList(tec1)
        clienteRepository.saveAll(List.of(cli1));
        chamadoRepository.saveAll(List.of(c1));
    }
}
