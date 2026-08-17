package com.jsp.sagar_shopee.service;

import java.util.List;

import com.jsp.sagar_shopee.model.Product;
import com.jsp.sagar_shopee.request.AddProductRequest;
import com.jsp.sagar_shopee.request.ProductUpdateRequest;

public interface ProductService {

	Product addProduct(AddProductRequest request);

	Product getProductById(long id);

	void deleteProductById(long id);

	Product updateProduct(ProductUpdateRequest product, long productId);

	List<Product> getAllProducts();

	List<Product> getProductsByCategory(String category);

	List<Product> getProductsByBrand(String brand);

	List<Product> getProductsByCategoryAndBrand(String category, String brand);

	List<Product> getProductByName(String name);

	List<Product> getProductByBrandAndName(String brand, String name);

	long countProductByBrandAndName(String brand, String name);

}
