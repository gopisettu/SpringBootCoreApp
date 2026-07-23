package com.springboot.ecom.service;

import com.springboot.ecom.dto.CustomeResDto;
import com.springboot.ecom.dto.CustomerDto;
import com.springboot.ecom.dto.CustomerUpdateDto;
import com.springboot.ecom.dto.response.Product.OrderDto;
import com.springboot.ecom.enums.Role;
import com.springboot.ecom.exception.ResourseNotFoundException;
import com.springboot.ecom.mapper.CustomerMapper;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.CustomerRepository;
import com.springboot.ecom.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private  final CustomerMapper customerMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public CustomeResDto add(CustomerDto dto) {
        User user=new User();
        user.setUsername(dto.username());
        user.setRole(Role.CUSTOMER);
        //Encoding Password
user.setPassword(passwordEncoder.encode(dto.password()));
userRepository.save(user);

        Customer customer=customerMapper.getMapCustomerDto(dto);
        customer.setUser(user);
        customerRepository.save(customer);

        CustomeResDto customerResDto =customerMapper.mapEntityToDto(customer);
        return customerResDto;

    }

    public List<CustomeResDto> getAll(int page,int size) {

       Pageable pageable= PageRequest.of(page,size);
//        List<Customer> customer=customerRepository.findAll(pageable).getContent();

        List<Customer>customer=customerRepository.fetchAll(pageable).getContent();
return  customer.stream()
        .map((c)->customerMapper.mapEntityToDto(c))
        .toList();

    }

    public CustomeResDto getAllById(long id) {
        Customer customer= customerRepository.findById(id)
                .orElseThrow(()-> new ResourseNotFoundException("Customer Id not found"));

        return customerMapper.mapEntityToDto(customer);


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

    public CustomeResDto update(long id, @Valid CustomerUpdateDto customerUpdateDto) {
       Customer customerDB= customerRepository.findById(id)
                .orElseThrow(()->new ResourseNotFoundException("Customer Id not found"));
       customerDB.setCity(customerUpdateDto.city());
       customerRepository.save(customerDB);
       return customerMapper.mapEntityToDto(customerDB);
    }

    public CustomeResDto fetchById(long id) {
        Customer customer=customerRepository.fetchById(id)
                .orElseThrow(()->new ResourseNotFoundException("Customer Id not found"));
        if(customer == null)
            System.out.println("Customer Id Not found");

        return customerMapper.mapEntityToDto(customer);

    }

    public List<OrderDto> getProductsPurchasedByCustomer(String customerUsername,int page,int size) {
        Pageable pageable=PageRequest.of(page,size);
        return  customerRepository.getProductsPurchasedByCustomer(customerUsername,pageable);
    }
}
