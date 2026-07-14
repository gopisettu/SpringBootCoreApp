package com.springboot.ecom.dto.response.Product;

public record ProductResByTitleSearch(
        String title,
        String description,
        double price,
        String sellerName,
        String categoryName
) {

}
