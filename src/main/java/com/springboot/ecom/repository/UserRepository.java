package com.springboot.ecom.repository;

import com.springboot.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("""
           Select u from User u where u.username=?1  AND u.role="SELLER"
            """)
    Optional<User> getSellerByUserName(String username);
}
