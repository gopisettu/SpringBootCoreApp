package com.springboot.ecom.SpringSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
     public UserDetailsService setUserNameAndPassword(){
        UserDetails customer1= User.builder()
                .username("customer1")
                .password("customer1")
                .roles("CUSTOMER")
                .build();
        UserDetails customer2=User.builder()
                        .username("customer2")
                        .password("customer2")
                        .roles("CUSTOMER")
                                .build();
        UserDetails execuator=User.builder()
                .username("execuator1")
                .password("execuator")
                .roles(" EXECUTIVE")
                .build();
        return  new InMemoryUserDetailsManager(customer2,customer1,execuator);


    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().hasRole("EXECUTIVE")
                        .requestMatchers("/api/product/get-category/pageable/{categoryId}").permitAll()
                                .anyRequest().authenticated()


                );
        return httpSecurity.build();
    }
}
