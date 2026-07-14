package com.springboot.ecom.dto.response.Product;

public record ProductResDto(
        long id,
        String title,
        double price,
        String sellerName
) {
}
