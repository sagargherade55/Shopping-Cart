package com.jsp.sagar_shopee.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.sagar_shopee.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>{

	Category findByName(String name);

	Category findCategoryByName(String name);

	boolean existByName(String name);
}
