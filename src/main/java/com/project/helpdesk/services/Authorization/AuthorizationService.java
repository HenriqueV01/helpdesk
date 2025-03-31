package com.project.helpdesk.services.Authorization;

import com.project.helpdesk.repositories.PessoaRepository;
import com.project.helpdesk.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    /*@Autowired
    PessoaRepository repository;*/ //<- Acho que pode ser usado a própria entidade Pessoa, em vez de criar uma entidade User apenas para logar no sistema.
    //De acordo com o exemplo do Valdir Cesar, Aula 24, minuto 31.
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(repository.findByLogin(username));
        return repository.findByLogin(username);
    }
}
