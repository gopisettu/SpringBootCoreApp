package com.springboot.ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Setter
@Getter
@AllArgsConstructor
@ToString
@Table(name="customer_product")
public class CustomerProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;
    @CreationTimestamp
      private Instant purchaseDate;

      private  int quantity;
      private double discount;
      @ManyToOne
      @JoinColumn(name = "customer_id",nullable = false)
      private Customer customer;
      @ManyToOne
      @JoinColumn(name="product_id",nullable = false)
      private Product product;

}
