package com.springboot.ecom.service;

import com.springboot.ecom.dto.Executive.ExecutiveUserDto;
import com.springboot.ecom.dto.response.ExecutiveResDtoByJobTitle;
import com.springboot.ecom.enums.JobTitle;
import com.springboot.ecom.enums.Role;
import com.springboot.ecom.mapper.ExecutiveMapper;
import com.springboot.ecom.mapper.UserMapper;
import com.springboot.ecom.model.Executive;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.ExecutiveRepository;
import com.springboot.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecutiveService {

    private final ExecutiveRepository executiveRepository;
    private  final UserRepository userRepository;
    public void insert(ExecutiveUserDto executiveUserDto) {
        //1. get user details from the Request body

        String username=executiveUserDto.username();
        String password=executiveUserDto.password();
        Role role=Role.EXECUTIVE;

// 2.insert into  new user and save it in Db and get back the saved user inorder to add it to executive
User user=new User();
user.setUsername(username);
user.setPassword(password);
user.setRole(role);

  user=userRepository.save(user);

// 3.fetch executive details from request body and save it into new Executive\
        String name=executiveUserDto.name();
        JobTitle jobTitle=executiveUserDto.jobTitle();
        Executive executive=new Executive();
        executive.setName(name);
        executive.setJobTitle(jobTitle);
//4.attach user obj to executive and save it
        executive.setUser(user);
        executive=executiveRepository.save(executive);


    }

    public void insertProf( ExecutiveUserDto executiveUserDto) {
        User user= UserMapper.mapUserDtoToEntity(executiveUserDto.username(),executiveUserDto.password(),Role.EXECUTIVE);
        user=userRepository.save(user);
        Executive executive=ExecutiveMapper.mapDtoToEntiry(executiveUserDto.jobTitle(),executiveUserDto.name());
        executive.setUser(user);
        executiveRepository.save(executive);
    }


    public List<ExecutiveResDtoByJobTitle> getByJobTitle(JobTitle jobTitle) {
        return executiveRepository.getByJobTitle(jobTitle);
    }
}
