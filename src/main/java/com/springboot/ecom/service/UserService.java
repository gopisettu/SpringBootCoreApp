package com.springboot.ecom.service;

import com.springboot.ecom.dto.AdminDto;
import com.springboot.ecom.enums.Role;
import com.springboot.ecom.exception.InvalidCredentialsException;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addAdmin(AdminDto adminDto) {
        User user=new User();
        user.setUsername(adminDto.username());
        user.setPassword( passwordEncoder.encode(adminDto.password()));
        user.setRole(Role.ADMIN);
        user.setActive(true);
        userRepository.save(user);

    }
    public User getUserDetails(String loggedInUsername) {
        return userRepository.loadUserByUsername(loggedInUsername)
                .orElseThrow(()-> new InvalidCredentialsException("Login Denied"));
    }

}
