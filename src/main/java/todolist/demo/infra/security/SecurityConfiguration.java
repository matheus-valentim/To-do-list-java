package todolist.demo.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.SessionManagementDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
    return httpSecurity
            .csrf(csrf ->csrf.disable())
                    .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize ->authorize
                    .requestMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    .requestMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/user/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/user/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/user/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/tarefas").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/tarefas").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/tarefas/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.DELETE, "/tarefas/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/tarefas/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "/tarefas/{id}").hasRole("USER")
                    .anyRequest().authenticated())
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
