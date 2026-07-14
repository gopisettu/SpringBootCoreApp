package com.springboot.ecom.service;

import com.springboot.ecom.dto.response.Product.ProductCountResDto;
import com.springboot.ecom.dto.response.Product.ProductDto;
import com.springboot.ecom.dto.response.Product.ProductResByTitleSearch;
import com.springboot.ecom.dto.response.Product.ProductResDto;
import com.springboot.ecom.exception.ResourseNotFoundException;
import com.springboot.ecom.mapper.ProductMapper;
import com.springboot.ecom.model.Category;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.Seller;
import com.springboot.ecom.repository.CategoryRepository;
import com.springboot.ecom.repository.ProductRepository;
import com.springboot.ecom.repository.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private  final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public void add(long sellerId, long categoryId, ProductDto productDto) {
        Seller seller=sellerRepository.findById(sellerId)
                .orElseThrow(()->new ResourseNotFoundException("Seller Id Not found"));
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourseNotFoundException("Category Id not found"));
        Product product= ProductMapper.mapProductDtoToEntity(productDto);
        product.setSeller(seller);
        product.setCategory(category);
        productRepository.save(product);
    }

    public List<ProductResDto> getAllProductsByCategoryId(int categoryId) {
        List<Product> products=productRepository.getAllProductsByCategoryId(categoryId);
      return products.stream()
                .map((p)->ProductMapper.mapEntityToProductDto(p))
                .toList();

    }

    public List<ProductResDto> getAllProductsByCategoryIdPageable(int categoryId, int page, int size) {
        Pageable pageabl=PageRequest.of(page,size);
        List<Product> products=productRepository.getAllProductsByCategoryIdPageable(categoryId,pageabl);
        return products.stream()
                .map((p)->ProductMapper.mapEntityToProductDto(p))
                .toList();

    }

    public List<ProductResByTitleSearch> getByTitle(String title) {
        return  productRepository.getByTitle(title);

    }

    public ProductCountResDto getProductBySeller(int sellerId) {
        return productRepository.getProductBySeller(sellerId);
    }
}
