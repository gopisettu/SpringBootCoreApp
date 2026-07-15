package com.springboot.ecom.controller;


import com.springboot.ecom.dto.Seller.SellerDto;
import com.springboot.ecom.service.SellerService;
import jakarta.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class SellerController {
    private final SellerService sellerService;
    @PostMapping("/insertSellerByExecutive/{executiveId}")
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
    public void insertSellerByExecutive(@PathVariable long executiveId, @RequestBody SellerDto sellerDto)
    {
        sellerService.addSellerByExecutive(executiveId,sellerDto);
    }
    @DeleteMapping("/de-activateSeller")
    public void deActivateSeller(@RequestParam String username){
        sellerService.deActivateSeller(username);
    }
}
