package com.jsp.sagar_shopee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.sagar_shopee.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

	Category findByName(String name);
}
