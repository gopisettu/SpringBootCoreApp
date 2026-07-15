package com.springboot.ecom.repository;

import com.springboot.ecom.model.Seller;
import com.springboot.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller,Long> {



}
