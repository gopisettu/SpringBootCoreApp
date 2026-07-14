package com.springboot.ecom.controller;

import com.springboot.ecom.dto.Executive.ExecutiveUserDto;
import com.springboot.ecom.dto.response.ExecutiveResDtoByJobTitle;
import com.springboot.ecom.enums.JobTitle;
import com.springboot.ecom.service.ExecutiveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/executive")
   /*
    Executive with User
    Body:
    {
        name : "",
        jobTitle : "",
        username : "",
        password: ""
    }
    * */
public class ExecutiveController {
    private  final ExecutiveService executiveService;
    @PostMapping("/insert/executiveUser")
    public  void insertExecutiveUser(@RequestBody ExecutiveUserDto executiveUserDto){
         executiveService.insert(executiveUserDto);

    }

    @GetMapping("/get-ByJobTitle")
    public List<ExecutiveResDtoByJobTitle> getByJobTitle(JobTitle jobTitle){
        return executiveService.getByJobTitle(jobTitle);
    }


    @PostMapping("/insert/executiveUser/prof")
    public  void insertExecutiveUserProf( @Valid  @RequestBody ExecutiveUserDto executiveUserDto){
        executiveService.insertProf(executiveUserDto);

    }


}
