package com.jsp.sagar_shopee.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.sagar_shopee.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{

	List<Product> findByCategory(String category);

	List<Product> findProductByBrand(String brand);

	List<Product> findProductsByCategoryAndBrand(String category, String brand);

	List<Product> findProductByName(String name);

	List<Product> findProductByBrandAndName(String brand, String name);

	long countByBrandAndName(String brand, String name);

}
