package com.springboot.ecom.Service;

import com.springboot.ecom.dto.CustomeResDto;
import com.springboot.ecom.dto.CustomerDto;
import com.springboot.ecom.dto.CustomerUpdateDto;
import com.springboot.ecom.enums.Role;
import com.springboot.ecom.exception.ResourseNotFoundException;
import com.springboot.ecom.mapper.CustomerMapper;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.CustomerRepository;
import com.springboot.ecom.repository.UserRepository;
import com.springboot.ecom.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CustomerMapper customerMapper;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public  void init(){
        user1 = new User(1L,true,"hari@gmail.com","hari@123",Role.ADMIN);
        user2=new User(2L,true,"gopi@gmail.com","gopi@123",Role.ADMIN);
        user3=new User(3L,true,"bhaobal@gmail.com","gopal@123",Role.ADMIN);
        customer1 = new Customer(1L,"John Doe","hubli",true, user1);
        customer2 = new Customer(2L,"Arjun","jamica",true, user2);
        customer3 = new Customer(3L,"Sharmila","shikkim",true, user3);

    }

    @Test
    public void getByIdPresent(){
    when(customerRepository.findById(9L)).thenReturn(Optional.of(customer1));
        CustomeResDto customeResDto= CustomerMapper.mapEntityToDto(customer1);

        Assertions.assertEquals(customeResDto,customerService.getAllById(9L));
        verify(customerRepository, times(1)).findById(9L);
    }
    @Test public void getByIdNotPresent(){
        when(customerRepository.findById(9L)).thenReturn(Optional.empty());
//        Assertions.assertEquals("Customer Id not found",customerService.getAllById(9000L));
        Assertions.assertEquals("Customer Id not found",
                Assertions.assertThrows(ResourseNotFoundException.class, ()-> {
                    customerService.getAllById(9L);
                }).getMessage() );

        verify(customerRepository,times(1)).findById(9L);
    }
    @Test
    public void getAllCustomerTest(){
        int page=0;
        int size=2;
        Pageable pageable= PageRequest.of(page,size);
        Page<Customer> customerPage=new PageImpl<>(List.of(customer1,customer2));
        when(customerRepository.fetchAll(pageable)).thenReturn(customerPage);
        Assertions.assertEquals(2,customerService.getAll(0,2).size());


        size=3;
        Pageable pageable2 =  PageRequest.of(page,size);
        customerPage = new PageImpl<>(List.of(customer1,customer2,customer3));
        when(customerRepository.fetchAll(pageable2)).thenReturn(customerPage);
        Assertions.assertEquals(3,customerService.getAll(0,3).size());

        verify(customerRepository,times(1)).fetchAll(pageable);

        verify(customerRepository,times(1)).fetchAll(pageable2);
    }

    @Test
    public void updateTestForInvalidCustomer() {

        CustomerUpdateDto customerDto = new CustomerUpdateDto(
                2,
                "Salem"
        );

        when(customerRepository.findById(11L))
                .thenReturn(Optional.empty());

        ResourseNotFoundException exception =
                Assertions.assertThrows(
                        ResourseNotFoundException.class,
                        () -> customerService.update(11L, customerDto)
                );

        Assertions.assertEquals("Customer Id not found", exception.getMessage());

        verify(customerRepository, times(1)).findById(11L);
    }

    @Test
    public  void addTest(){
        user1.setPassword("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user1);
        when(customerRepository.save(any(Customer.class))).thenReturn((customer1));

        when(passwordEncoder.encode("hari@123")).thenReturn("encodedPassword");
        CustomerDto customerDto = new CustomerDto(
                "Hari Krishnan",
                "Thirupur",
                "hari@gmail.com",
                "hari@123"
        );
        customerService.add(customerDto);
        ArgumentCaptor<Customer> customerCaptor =  ArgumentCaptor.forClass(Customer.class);


        verify(customerRepository, times(1)).save(customerCaptor.capture());
   Assertions.assertEquals(customerDto.city(),customerCaptor.getValue().getCity());
   Assertions.assertEquals(customerDto.name(),customerCaptor.getValue().getName());






    }

}
