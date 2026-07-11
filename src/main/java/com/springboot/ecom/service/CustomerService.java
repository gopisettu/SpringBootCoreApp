package com.springboot.ecom.service;

import com.springboot.ecom.dto.CustomeResDto;
import com.springboot.ecom.dto.CustomerDto;
import com.springboot.ecom.exception.ResourseNotFoundException;
import com.springboot.ecom.mapper.CustomerMapper;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private  final CustomerMapper customerMapper;


    public Customer add(CustomerDto dto) {
        Customer customer=customerMapper.getMapCustomerDto(dto);
        return  customerRepository.save(customer);
    }

    public List<CustomeResDto> getAll(int page,int size) {

       Pageable pageable= PageRequest.of(page,size);
        List<Customer> customer=customerRepository.findAll(pageable).getContent();
return  customer.stream()
        .map((c)->customerMapper.mapEntityToDto(c))
        .toList();

    }

    public Customer getAllById(long id) {
        Customer customer= customerRepository.findById(id)
                .orElseThrow(()-> new ResourseNotFoundException("Customer Id not found"));
        return  customer;


    }

//    Hard Delete
//    public void delete(long id) {
//        Customer customer= customerRepository.findById(id)
//                .orElseThrow(()-> new ResourseNotFoundException("Customer Id not found"));
//        customerRepository.delete(customer);
//    }


//    Soft Delete using is_Active,flip into false

        public void delete(long id) {
        Customer customer= customerRepository.findById(id)
                .orElseThrow(()-> new ResourseNotFoundException("Customer Id not found"));
        customer.set_active(false);

        customerRepository.save(customer);
    }

}
