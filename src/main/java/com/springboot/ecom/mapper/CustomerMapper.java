package com.springboot.ecom.mapper;

import com.springboot.ecom.dto.CustomeResDto;
import com.springboot.ecom.dto.CustomerDto;
import com.springboot.ecom.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer getMapCustomerDto(CustomerDto dto){
        Customer customer=new Customer();
        customer.setName(dto.name());
        customer.setCity(dto.city());
        return  customer;

    }
    public CustomeResDto mapEntityToDto(Customer customer){
        CustomeResDto customeResDto=new CustomeResDto(customer.getName(),customer.getCity());
        return customeResDto;


    }
}
