package gestionInventario.com.config;


import gestionInventario.com.config.jwt.JwtAuthenticationFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import gestionInventario.com.model.enumerator.user.Role;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityConfig {
    final JwtAuthenticationFilter jwtAuthenticationFilter;
    final AuthenticationProvider authenticationProvider;

    private final static String ADMIN = Role.ADMIN.toString();
    private final static String CUSTOMER = Role.CUSTOMER.toString();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(authRequest -> {
                    configureCustomerEndPoints(authRequest);
                    configurePublicEndpoints(authRequest);
                    configureAdminEndpoints(authRequest);
                })
                
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    private void configurePublicEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST,"/auth/register").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                //.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

    }

    private void configureCustomerEndPoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST,"/cart/add").hasRole(CUSTOMER)
                .requestMatchers(HttpMethod.PATCH,"/cart/delete").hasRole(CUSTOMER)
                .requestMatchers(HttpMethod.GET,"/cart").hasRole(CUSTOMER)
                .requestMatchers(HttpMethod.POST,"/order/create/{cartId}").hasRole(CUSTOMER)
                .requestMatchers(HttpMethod.GET, "/product/get-all").hasAnyRole(CUSTOMER,ADMIN)
                .requestMatchers(HttpMethod.POST, "/email/welcome").hasAnyRole(CUSTOMER,ADMIN)
                .requestMatchers(HttpMethod.PATCH, "/product/update-stock").hasRole(CUSTOMER)
                .requestMatchers(HttpMethod.PATCH, "/item/update-stock").hasRole(CUSTOMER)

                .requestMatchers(HttpMethod.GET,"/order/get-orders").hasRole(CUSTOMER);
    }

    private void configureAdminEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST,"/product/create").hasRole(ADMIN)
                .requestMatchers(HttpMethod.GET,"/customer/all-customers").hasRole(ADMIN);

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization", "Access-Control-Allow-Origin"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}