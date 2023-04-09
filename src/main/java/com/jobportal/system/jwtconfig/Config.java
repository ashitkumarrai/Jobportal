package com.jobportal.system.jwtconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class Config {

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	// Inject this field value directly into "filterChain", the only method that
	// uses it
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtRequestFilter jwtRequestFilter)
			throws Exception {

		final String[] WHITELIST_URL = {
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/v3/api-docs",
				"/auth/**",
				"/register/**",
				"/h2-console/**",
				"/webjars/**",
				"/media/**",
				"/candidate/signup/**",
				"/employer/signup/**",
				"/verify/**",
				"/authenticate/**"
				

		};


        httpSecurity
        .csrf(csrf -> csrf.disable()).cors(cors->cors.disable())
        .authorizeHttpRequests(auth -> auth
        .requestMatchers(WHITELIST_URL).permitAll()
				

        .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAnyAuthority("ADMIN")
        .requestMatchers(new AntPathRequestMatcher("/user/**")).hasAnyAuthority("ADMIN", "USER")
        .requestMatchers(new AntPathRequestMatcher("/employer/**")).hasAnyAuthority("EMPLOYER")
        //posts Methods
        
        
        

        .anyRequest().authenticated()

        ).exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    
        .build();




		
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
}

