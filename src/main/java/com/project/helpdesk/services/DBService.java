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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService {

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;

    public void instaciaDB(){
        Tecnico tec1 = new Tecnico(null, "Henrique", "000.000.000-00", "tec01@email.com", "123456");
        tec1.addPerfil(Perfil.ADMIN);

        Cliente cli1 = new Cliente(null, "Maria", "111.111.111-11", "cli1@email.com", "123456");
        /*cli1.addPerfil(Perfil.CLIENTE);*/

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);

        tecnicoRepository.saveAll(List.of(tec1)); //Arrays.asList(terc1)
        clienteRepository.saveAll(List.of(cli1));
        chamadoRepository.saveAll(List.of(c1));
    }
}
