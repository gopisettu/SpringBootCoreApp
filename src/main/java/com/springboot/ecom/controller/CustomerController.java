package com.springboot.ecom.controller;

import com.springboot.ecom.dto.CustomeResDto;
import com.springboot.ecom.dto.CustomerDto;
import com.springboot.ecom.dto.CustomerUpdateDto;
import com.springboot.ecom.dto.response.Product.OrderDto;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.service.CustomerService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
//@ResponseBody
@RestController
//->implements @Controller,@ResponseEntity
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
//    @GetMapping("/api/hello")
//    public String sayHello(){
//        return "Hello , Hey Buddy";
//    }
//    @GetMapping("/api/hello/private")
//    public String sayHelloPrivate(){
//        return "GOAT";
//    }
//    @GetMapping("api/customer/name")
//    public List<String>getCustomerName(){
//        return  List.of("gopi","hari","raja");
//    }



    private final CustomerService customerService;
    //@RequiredArgsConstructor

    @PostMapping("/add")
    public CustomeResDto add( @Valid @RequestBody CustomerDto dto){
        return customerService.add(dto);

    }
//    @GetMapping("/get-all")
//    public List<CustomeResDto> getAll(){
//        return customerService.getAll();
//    }


        @GetMapping("/get-all")
    public List<CustomeResDto> getAll(@RequestParam Integer page,@RequestParam Integer size){
        return customerService.getAll(page,size);
    }

    @GetMapping("/get-oneById/{id}")
    public CustomeResDto getAllById(@PathVariable long id){
        return customerService.getAllById(id);
    }

        @DeleteMapping("/delete-byId/{id}")
    public  void delete(@PathVariable long id){
         customerService.delete(id);

    }
    @PutMapping("/update-ById/{id}")
    public CustomeResDto update(@PathVariable long id,  @Valid @RequestBody CustomerUpdateDto customerUpdateDto){
         return customerService.update(id,customerUpdateDto);
    }

@GetMapping("/fetch-allByID/{id}")
    public  CustomeResDto fetchById(@PathVariable long id){
        return  customerService.fetchById(id);
}


    @GetMapping("/purchase/by-customer")
    public List<OrderDto>getProductsPurchasedByCustomer(@RequestParam String customerUsername,
                                                        @RequestParam(required = false,defaultValue = "0") int page,
                                                        @RequestParam(required = false,defaultValue = "20") int size){
        return customerService.getProductsPurchasedByCustomer(customerUsername,page,size);
    }

}


