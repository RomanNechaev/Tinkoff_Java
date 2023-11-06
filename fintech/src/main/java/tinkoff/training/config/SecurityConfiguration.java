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
    private static final String[] ENDPOINTS = {
            "/repository/jdbc/city/**",
            "/repository/jdbc/weather/**",
            "/repository/jdbc/type/**",
            "/repository/jpa/city/**",
            "/repository/jpa/weather/**",
            "/repository/jpa/type/**",
            "/api/weather/{city}"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/**"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/repository/**")).hasRole(Role.ADMIN.name())
                        .requestMatchers(matchers(HttpMethod.GET, ENDPOINTS)).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers(matchers(HttpMethod.PUT, ENDPOINTS)).hasRole(Role.ADMIN.name())
                        .requestMatchers(matchers(HttpMethod.POST, ENDPOINTS)).hasRole(Role.ADMIN.name())
                        .requestMatchers(matchers(HttpMethod.DELETE, ENDPOINTS)).hasRole(Role.ADMIN.name())
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults())
                .logout(logout ->
                        logout.logoutUrl("/api/auth/logout"));
        return http.build();
    }

    public AntPathRequestMatcher[] matchers(HttpMethod httpMethod, String... patterns) {
        AntPathRequestMatcher[] matchers = new AntPathRequestMatcher[patterns.length];
        for (int index = 0; index < patterns.length; index++) {
            matchers[index] = new AntPathRequestMatcher(patterns[index], httpMethod.name());
        }
        return matchers;
    }
}
