package tinkoff.training.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import tinkoff.training.entities.Role;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String API_PATH = "/api/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/**"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, API_PATH)).hasRole(Role.ADMIN.name())
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, API_PATH)).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, API_PATH)).hasRole(Role.ADMIN.name())
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, API_PATH)).hasRole(Role.ADMIN.name())
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, API_PATH)).hasRole(Role.ADMIN.name())
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults())
                .logout(logout ->
                        logout.logoutUrl("/api/auth/logout"));
        return http.build();
    }
}
