package com.project.ecommerce.security;

import com.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Autowired
    private UserService userInformationService;

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

//    @Autowired
//    private JwtAuthFilter jwtAuthFilter;
//    
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.cors().and().csrf().disable()
//                .authorizeHttpRequests().requestMatchers("/login", "/logOut", "/checkTokenValidity").permitAll()
//                .anyRequest().authenticated()
//                .and().exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint);
//
//        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        http.authenticationProvider(this.authenticationProvider());
//
//        return http.build();
        http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers(
                "/login", "/logOut", "/checkTokenExpiry", "/checkTokenValidity").permitAll()
                .anyRequest().authenticated())
                .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint));

        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(this.authenticationProvider());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService((UserDetailsService) this.userInformationService);
        authProvider.setPasswordEncoder(this.passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // return NoOpPasswordEncoder.getInstance();
    }
    
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
    	return new JwtAuthFilter();
    }

}
