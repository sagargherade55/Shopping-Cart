package com.jsp.sagar_shopee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.sagar_shopee.model.Product;
import com.jsp.sagar_shopee.repo.ProductRepo;
import com.jsp.sagar_shopee.request.AddProductRequest;
import com.jsp.sagar_shopee.service.ProductService;

@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/add")
	public ResponseEntity<Product> addProduct(@RequestBody AddProductRequest product) {
		Product savedProduct = productService.addProduct(product);
	    return ResponseEntity.status(201).body(savedProduct);
	}
	
}
