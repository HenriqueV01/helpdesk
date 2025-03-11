package com.project.helpdesk.infra.security;

import com.project.helpdesk.domain.user.User;
import com.project.helpdesk.repositories.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/tecnicos").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserRepository userRepository) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(username -> {
            User user = (User) userRepository.findByLogin(username);
            if (user == null) {
                throw new UsernameNotFoundException("Usuário não encontrado");
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getLogin(),
                    user.getPassword(), // A senha já está criptografada
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())) // Adiciona o papel do usuário
            );
        });
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder()); // Garante que a senha seja verificada corretamente
        return authProvider;
    }

   /* @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
        return new ProviderManager(List.of(authenticationProvider));
    }

    //==================================================================================================================

    /*Aqui abaixo estamos substituindo o DaoAuthenticationProvider por uma verificação manual.
    Carregamos o usuário via UserDetailsService.
    Comparamos a senha manualmente usando passwordEncoder.matches().
    Se as credenciais estiverem corretas, criamos um UsernamePasswordAuthenticationToken.*/

    /*@Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        return authentication -> {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Verifica a senha manualmente
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Credenciais inválidas");
            }

            return new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );
        };
    }*/

    //==================================================================================================================

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //==================================================================================================================

    /*Se o Spring não encontrar o usuário corretamente, ele retornará 403.
    Certifique-se de que o UserDetailsService está carregando os usuários corretamente:*/

    /*@Service
    public class UserDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByLogin(username);
            if (user == null) {
                throw new UsernameNotFoundException("Usuário não encontrado");
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getLogin(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
            );
        }
    }*/

    //==================================================================================================================

    //Essa classe buscará o usuário no banco e fornecerá os dados ao Spring Security:

    // Dois UserDetailsService iguais?

    /*@Service
    public class UserDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByLogin(username);
            if (user == null) {
                throw new UsernameNotFoundException("Usuário não encontrado");
            }

            return new org.springframework.security.core.userdetails.User(
                    user.getLogin(),
                    user.getPassword(), // Senha criptografada no banco
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
            );
        }
    }*/

}
