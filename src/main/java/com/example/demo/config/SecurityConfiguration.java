package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.sec.CustomUserDetailsOauthService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
	CustomUserDetailsOauthService cudos;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(req -> 
						req.requestMatchers(new AntPathRequestMatcher("/user/**")).hasRole("USER")
						.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
						.requestMatchers(new AntPathRequestMatcher("/books/leaveReview")).hasRole("USER")
						.requestMatchers(new AntPathRequestMatcher("/books/**")).hasAnyRole("USER", "ADMIN")
						.requestMatchers(new AntPathRequestMatcher("/cart/**")).hasRole("USER")
						.requestMatchers(new AntPathRequestMatcher("/**")).permitAll().anyRequest().authenticated()
						)
				.formLogin(login -> 
						login.loginPage("/login")
						.permitAll()
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/books/search")
						)
				.logout(out-> out.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll())
				.csrf(csfr -> csfr.disable())
				.oauth2Login(oauth2Login -> oauth2Login.loginPage("/login")
						.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(cudos)))
				.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
