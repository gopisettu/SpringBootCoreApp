package com.springboot.ecom.mapper;

import com.springboot.ecom.dto.response.Product.ProductDto;
import com.springboot.ecom.dto.response.Product.ProductResDto;
import com.springboot.ecom.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public  static Product mapProductDtoToEntity(ProductDto productDto){
        Product product=new Product();
        product.setTitle(productDto.title());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        return product;
    }
    public static ProductResDto mapEntityToProductDto(Product product){
        return  new ProductResDto(product.getId(),product.getTitle(), product.getPrice(),product.getSeller().getName());
    }
}
