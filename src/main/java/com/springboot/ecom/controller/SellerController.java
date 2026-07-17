package com.springboot.ecom.controller;


import com.springboot.ecom.dto.Seller.SellerDto;
import com.springboot.ecom.service.SellerService;
import jakarta.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class SellerController {
    private final SellerService sellerService;

    /**
     * Executive Add seller detail
     * Body of user credentials{
     *     name,
     *     contact,
     *     city,
     *
     *     username,
     *     password
     * }
     *
     */
    @PostMapping("/insertSellerByExecutive")
    public void insertSellerByExecutive(
            Principal principal,
            @RequestBody SellerDto sellerDto) {

        String executiveName = principal.getName();
        sellerService.addSellerByExecutive(executiveName, sellerDto);
    }
    @DeleteMapping("/de-activateSeller/{sellerUsername}")
    public void deActivateSellerByExecutive(Principal principal,@PathVariable String sellerUsername){

        String executive= principal.getName();
        sellerService.deActivateSeller(sellerUsername,executive);
    }
}
