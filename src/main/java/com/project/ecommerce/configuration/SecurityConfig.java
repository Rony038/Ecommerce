package com.project.ecommerce.configuration;

import java.beans.Customizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import com.project.ecommerce.security.CustomUserDetailsService;
import com.project.ecommerce.security.JwtAuthEntryPoint;
import com.project.ecommerce.security.JwtAuthFilter;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtAuthEntryPoint jwtAuthEntryPoint;
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	        http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
	                .csrf(csrf -> csrf.disable()).authorizeHttpRequests(request -> request
	                .requestMatchers("/login").permitAll()
	                .anyRequest().authenticated())
	                .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint));

	        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

	        http.authenticationProvider(this.authenticationProvider());
	        return http.build();
	    }

	    @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        
	        authProvider.setUserDetailsService((UserDetailsService) this.customUserDetailsService);
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
}
