package com.springboot.ecom.controller;

import com.springboot.ecom.dto.response.Product.ProductCountResDto;
import com.springboot.ecom.dto.response.Product.ProductDto;
import com.springboot.ecom.dto.response.Product.ProductResByTitleSearch;
import com.springboot.ecom.dto.response.Product.ProductResDto;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    @PostMapping("/add/{sellerId}/{categoryId}")
    public void add(@PathVariable  long sellerId,
                    @PathVariable  long categoryId,
                 @Valid @RequestBody ProductDto productDto){

        productService.add(sellerId,categoryId,productDto);
    }


    @GetMapping("/get-category/pageable/{categoryId}")
    public List<ProductResDto> getAllProductsByCategoryId(@PathVariable int categoryId,
                                                          @RequestParam(required = false,defaultValue = "0") int page,
                                                          @RequestParam(required = false,defaultValue = "20") int size){
        return productService.getAllProductsByCategoryIdPageable(categoryId,page,size);
    }

    @GetMapping("/get-ByTitle")
    public  List<ProductResByTitleSearch> getByTitle(@RequestParam String title){
        return productService.getByTitle(title);
    }

    @GetMapping("/getProdutCountBySeller/{sellerId}")
    public ProductCountResDto getProductBySeller(@PathVariable int sellerId){
        return productService.getProductBySeller(sellerId);
    }


}
