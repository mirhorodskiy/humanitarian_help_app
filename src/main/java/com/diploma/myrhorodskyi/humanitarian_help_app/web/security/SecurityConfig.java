package com.diploma.myrhorodskyi.humanitarian_help_app.web.security;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;

    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/volunteers/all").permitAll()
                .antMatchers("/volunteers/profile/**").hasAnyRole(Role.VOLUNTEER.name())
                .antMatchers("/volunteers/{id}").permitAll()
//                .and()
//                .authorizeRequests()
                .antMatchers("/users/**").hasAnyRole(Role.USER.name())
                .antMatchers("/request/create").hasAnyRole(Role.USER.name())
                .antMatchers("/request/all/**").permitAll()
                .antMatchers("/request/my").hasAnyRole(Role.USER.name())
                .antMatchers("/request/{id}").permitAll()
                .antMatchers("/request/delete/**").hasAnyRole(Role.USER.name())
                .antMatchers("/request/change").hasAnyRole(Role.USER.name())
                .antMatchers("/request/approve/{id}").hasAnyRole(Role.USER.name())
                .antMatchers("/request/take/**").hasAnyRole(Role.VOLUNTEER.name())
                .antMatchers("/request/admin/**").hasAnyRole(Role.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
