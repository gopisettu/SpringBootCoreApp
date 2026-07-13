package com.springboot.ecom.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String reviewText;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;
}
