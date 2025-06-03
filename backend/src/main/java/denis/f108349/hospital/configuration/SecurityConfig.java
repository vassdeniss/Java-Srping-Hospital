package denis.f108349.hospital.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .cors()
                .and()
                .authorizeExchange(exchanges -> exchanges
                    .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()    
                        
                    // Public endpoints
                    .pathMatchers("/swagger-ui.html", "/v3/api-docs/**", "/public/**").permitAll()
                    
                    // Admin endpoints
                    .pathMatchers("/api/admin/**").hasRole("admin")
                    .pathMatchers("/api/reports/**").hasRole("admin")
                    .pathMatchers(HttpMethod.POST, "/api/doctors/create").hasRole("admin")
                    .pathMatchers(HttpMethod.POST, "/api/doctors/assign/**").hasRole("admin")
                    .pathMatchers(HttpMethod.DELETE, "/api/patients/**").hasRole("admin")
                    
                    // Doctor endpoints
                    .pathMatchers(HttpMethod.POST, "/api/diagnosis/create").hasRole("doctor")
                    .pathMatchers(HttpMethod.PATCH, "/api/visits/**").hasRole("doctor")
                    .pathMatchers(HttpMethod.GET, "/api/medications/all").hasRole("doctor")
                    
                    // Patient endpoints
                    .pathMatchers(HttpMethod.POST, "/api/visits/create").hasRole("patient")
                    
                    // Shared endpoints
                    .pathMatchers(HttpMethod.GET, "/api/doctors/all").hasAnyRole("admin", "patient", "doctor")
                    .pathMatchers(HttpMethod.GET, "/api/doctors/**").hasAnyRole("admin", "doctor", "patient")
                    .pathMatchers(HttpMethod.GET, "/api/patients/**").hasAnyRole("admin", "patient", "doctor")
                    .pathMatchers(HttpMethod.GET, "/api/visits/**").hasAnyRole("patient", "doctor")
                    .pathMatchers(HttpMethod.PATCH, "/api/patients/**").hasAnyRole("admin", "patient")
                    
                    // Fallback
                    .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
                
        return http.build();
    }
    
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtAuthenticationConverter delegate = new JwtAuthenticationConverter();
        delegate.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess == null) return Collections.emptyList();
            
            Collection<String> roles = (Collection<String>) realmAccess.get("roles");
            return roles.stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        });
        
        return new ReactiveJwtAuthenticationConverterAdapter(delegate);
    }
}
