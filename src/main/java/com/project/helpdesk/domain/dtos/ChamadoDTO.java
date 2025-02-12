package com.project.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.helpdesk.domain.Chamado;
import com.project.helpdesk.domain.enums.Status;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ChamadoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;
    private Integer prioridade;
    private Status status;
    private String titulo;
    private String observacoes;

    private Integer tecnico;
    private String nomeTecnico;
    private Integer cliente;
    private String nomeCliente;

    public ChamadoDTO() {
        super();
    }

    public ChamadoDTO(Chamado obj) {
        this.id = obj.getId();
        this.dataAbertura = obj.getDataAbertura();
        this.dataFechamento = obj.getDataFechamento();
        this.prioridade = obj.getPrioridade().getCodigo();
        this.status = obj.getStatus();
        this.titulo = obj.getTitulo();
        this.observacoes = obj.getObservacoes();
        this.tecnico = obj.getTecnico().getId();
        this.nomeTecnico = obj.getTecnico().getNome();
        this.cliente = obj.getCliente().getId();
        this.nomeCliente = obj.getCliente().getNome();
    }
}
