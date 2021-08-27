package br.com.zupacademy.joao.propostas.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws  Exception {
        http.authorizeRequests(authorizeRequest ->
            authorizeRequest
                    .antMatchers(HttpMethod.POST, "/proposta").hasAuthority("SCOPE_propostas-scope")
                    .antMatchers(HttpMethod.GET, "/proposta/**").hasAuthority("SCOPE_propostas-scope")
                    .antMatchers(HttpMethod.POST, "/biometria/**").hasAuthority("SCOPE_propostas-scope")
                    .antMatchers(HttpMethod.PUT, "/bloqueio/**").hasAuthority("SCOPE_propostas-scope")
                    .antMatchers(HttpMethod.PUT, "/avisoviagem/**").hasAuthority("SCOPE_propostas-scope")
                    .antMatchers(HttpMethod.POST, "/paypal/**").hasAuthority("SCOPE_propostas-scope")
                    .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                    .antMatchers(HttpMethod.GET, "/actuator/prometheus").permitAll()
                    .anyRequest().authenticated()
        ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
