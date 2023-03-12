package org.example.resourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {

    public ResourceServerConfig(@Value("${foo.bar}") String port) {
        System.out.println("#");
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.mvcMatcher("/articles/**")
                .authorizeRequests()


                .mvcMatchers("/articles/**")
                //.access("hasAuthority('SCOPE_articles.read')")
                .authenticated()
                .and()
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        return http.build();
    }

    private final String issuerUri = "http://localhost:8080/realms/dev";


}
