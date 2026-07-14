package com.springboot.ecom.repository;

import com.springboot.ecom.dto.response.Product.ProductResByTitleSearch;
import com.springboot.ecom.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {
    @Query("""
            select p from Product p where p.category.id=?1
            """)
    List<Product> getAllProductsByCategoryId(int categoryId);

    @Query("""
            select p from Product p where p.category.id=?1
            """)
    List<Product> getAllProductsByCategoryIdPageable(int categoryId, Pageable pageabl);
//    Select p from Product p where p.title=?1;
    List<ProductResByTitleSearch> getByTitle(String title);



}
