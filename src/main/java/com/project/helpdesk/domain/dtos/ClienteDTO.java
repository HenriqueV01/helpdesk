package com.project.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.helpdesk.domain.Cliente;
import com.project.helpdesk.domain.enums.Perfil;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class ClienteDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected Integer id;
    @NotNull(message = "O campo NOME é requerido")
    protected String nome;
    @NotNull(message = "O campo CPF é requerido")
    protected String cpf;
    @NotNull(message = "O campo EMAIL é requerido")
    protected String email;
    @NotNull(message = "O campo SENHA é requerido")
    protected String senha;
    protected Set<Integer> perfis = new HashSet<>();
    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

    public ClienteDTO(){
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public ClienteDTO(Cliente cli){
        this.id = cli.getId();
        this.nome = cli.getNome();
        this.cpf = cli.getCpf();
        this.email = cli.getEmail();
        this.senha = cli.getSenha();
        this.perfis = cli.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = cli.getDataCriacao();
        addPerfil(Perfil.CLIENTE);
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }
    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

}
