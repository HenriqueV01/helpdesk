package com.project.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.helpdesk.domain.Tecnico;
import com.project.helpdesk.domain.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class TecnicoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected Integer id;
    protected String nome;
    protected String cpf;
    protected String email;
    protected String senha;
    protected Set<Integer> perfis = new HashSet<>();
    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

    public TecnicoDTO(){
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public TecnicoDTO(Tecnico tec){
        this.id = tec.getId();
        this.nome = tec.getNome();
        this.cpf = tec.getCpf();
        this.email = tec.getEmail();
        this.senha = tec.getSenha();
        this.perfis = tec.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = tec.getDataCriacao();
        addPerfil(Perfil.CLIENTE);
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }
    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

}
