package com.jsp.sagar_shopee.service;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.startup.Tomcat.ExistingStandardWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.sagar_shopee.exception.ProductNotFoundException;
import com.jsp.sagar_shopee.model.Category;
import com.jsp.sagar_shopee.model.Product;
import com.jsp.sagar_shopee.repo.CategoryRepo;
import com.jsp.sagar_shopee.repo.ProductRepo;
import com.jsp.sagar_shopee.request.AddProductRequest;
import com.jsp.sagar_shopee.request.ProductUpdateRequest;

import lombok.NoArgsConstructor;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepo productRepo;
	private CategoryRepo categoryRepo;

	@Autowired
	public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo) {
		this.productRepo = productRepo;
		this.categoryRepo = categoryRepo;
	}

	@Override
	public Product addProduct(AddProductRequest request) {
		Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategory().getName()))
				.orElseGet(() -> {
					Category newCategory = new Category(request.getCategory().getName());
					return categoryRepo.save(newCategory);
				});
		request.setCategory(category);
		return productRepo.save(createNewProduct(request, category));
	}

	private Product createNewProduct(AddProductRequest request, Category category) {
		return new Product(request.getName(), request.getBrand(), request.getPrice(), request.getInventory(),
				request.getDescription(), category);
	}

	@Override
	public Product getProductById(long id) {
		return productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found!"));
	}

	@Override
	public void deleteProductById(long id) {
		productRepo.findById(id).ifPresentOrElse(productRepo::delete, () -> {
			throw new ProductNotFoundException("Product Not Found!");
		});
	}

	@Override
	public Product updateProduct(ProductUpdateRequest request, long productId) {
		return productRepo.findById(productId).map(existingProduct -> updatExistingProduct(existingProduct, request))
				.map(productRepo::save).orElseThrow(() -> new ProductNotFoundException("Product not found"));
	}

	private Product updatExistingProduct(Product existingProduct, ProductUpdateRequest request) {
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());

		Category category = categoryRepo.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		return existingProduct;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepo.findByCategory(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepo.findProductByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		return productRepo.findProductsByCategoryAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductByName(String name) {
		return productRepo.findProductByName(name);
	}

	@Override
	public List<Product> getProductByBrandAndName(String brand, String name) {
		return productRepo.findProductByBrandAndName(brand, name);
	}

	@Override
	public long countProductByBrandAndName(String brand, String name) {
		return productRepo.countByBrandAndName(brand, name);
	}

}
