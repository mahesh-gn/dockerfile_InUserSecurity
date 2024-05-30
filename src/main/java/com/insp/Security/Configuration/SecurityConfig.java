package com.insp.Security.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(r -> r
                        .requestMatchers("/api/students/home").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/students/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/students/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/students/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/students/**").authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.builder().username("Abhi").password(passwordEncoder().encode("abhi")).roles("User").build();
        UserDetails admin = User.builder().username("Kishore").password(passwordEncoder().encode("kishore")).roles("Admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
