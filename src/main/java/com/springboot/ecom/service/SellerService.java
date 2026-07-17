package com.springboot.ecom.service;

import com.springboot.ecom.dto.Seller.SellerDto;
import com.springboot.ecom.enums.Role;
import com.springboot.ecom.exception.ResourseNotFoundException;
import com.springboot.ecom.mapper.ExecutiveMapper;
import com.springboot.ecom.mapper.UserMapper;
import com.springboot.ecom.model.Executive;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.Seller;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SellerService {
    private final ExecutiveRepository executiveRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;


    public void addSellerByExecutive(String executiveName, SellerDto sellerDto) {
        Seller seller = SellerMapper.mapSellerDtoToEntity(sellerDto);
//Encoding Password
        User user = UserMapper.mapUserDtoToEntity(sellerDto.username(), passwordEncoder.encode(sellerDto.password()), Role.SELLER);
        user = userRepository.save(user);
        Executive executive = executiveRepository.findByUserUsername(executiveName)
                .orElseThrow(() -> new ResourseNotFoundException("Executive Id not found"));
        seller.setUser(user);
        seller.setExcecutive(executive);
        sellerRepository.save(seller);

    }

    public void deActivateSeller(String username,String executiveUsername) {
        //step1 : get the seller seller(username) in User table,if not username found send resourse not found exception
         User user=userRepository.getSellerByUserName(username)
                 .orElseThrow(()->new RuntimeException("Seller username invalid"));
//         Seller seller=sellerRepository.findById(sellerId)
//                 .orElseThrow(()->new ResourseNotFoundException("Seller Id not Found"));

Executive executive=executiveRepository.findByUserUsername(executiveUsername)
        .orElseThrow(()->new ResourseNotFoundException("Executive Not Found"));
        //step2 : deactivate the isActive using seller Id and save it back
         user.setActive(false);
         userRepository.save(user);
        //step3 : update stockCount into o in the product table by patch(batch) method
        List<Product> list=productRepository.getProductsBySellerId(user.getId());
         list
                 .stream()
                 .peek((p)->p.setStockCount(0))
                 .toList();
         productRepository.saveAll(list);
        }
}
