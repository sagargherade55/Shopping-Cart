package com.jsp.sagar_shopee.service;

import java.util.List;

import com.jsp.sagar_shopee.model.Category;

public interface CategoryService {

	Category getCategoryById(long id);
	Category getCategoryByName(String name);
	List<Category> getAllCategories();
	Category addCategory(Category category);
	void deleteCategoryById(long id);
	Category updatCategory(Category category, long id);
}
