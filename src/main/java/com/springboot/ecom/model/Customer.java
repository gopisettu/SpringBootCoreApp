package com.springboot.ecom.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  long id;
    @Column(nullable = false)
   private  String name;
     private String city;
     private boolean is_active;
}
