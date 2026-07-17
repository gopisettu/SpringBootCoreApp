package com.springboot.ecom.config;

import com.springboot.ecom.service.MyUserAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SpringSecurityConfig {
    private final MyUserAuthenticationService myUserAuthenticationService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securedFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((c)->c.disable())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
//                        Public read-only product catalog
                                .requestMatchers(HttpMethod.GET, "/api/product/get-category/pageable/{categoryId}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/product/getProdutCountBySeller/{sellerId}").hasAuthority("SELLER")
                                .requestMatchers(HttpMethod.GET, "/api/product/get-ByTitle").permitAll()

//                        Public self-registration
                                .requestMatchers(HttpMethod.POST, "/api/customer/add").hasAuthority("ADMIN")

//                                .requestMatchers(HttpMethod.POST, "/api/admin/add").permitAll()

//                        Admin/Executive account & seller management
                                .requestMatchers(HttpMethod.POST, "/api/executive/insert/executiveUser").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/executive/insert/executiveUser/prof").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/executive/get-ByJobTitle").hasAnyAuthority("ADMIN", "EXECUTIVE")
                                .requestMatchers(HttpMethod.POST, "/api/seller/insertSellerByExecutive").hasAnyAuthority("ADMIN", "EXECUTIVE")
                                .requestMatchers(HttpMethod.DELETE, "/de-activateSeller/{sellerUsername}").hasAuthority("EXECUTIVE")

//                        Product writes
                                .requestMatchers(HttpMethod.POST, "/api/product/add/{sellerId}/{categoryId}").hasAnyAuthority("ADMIN", "EXECUTIVE", "SELLER")

//                        Customer management (self-service + staff)
                                .requestMatchers(HttpMethod.GET, "/api/customer/purchase/by-customer").permitAll()
                                .requestMatchers("/api/customer/**").hasAnyAuthority("ADMIN", "EXECUTIVE")
                                .requestMatchers("/api/auth/login").authenticated()
//
                                .anyRequest().authenticated()

                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider dao=new DaoAuthenticationProvider(myUserAuthenticationService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }
}