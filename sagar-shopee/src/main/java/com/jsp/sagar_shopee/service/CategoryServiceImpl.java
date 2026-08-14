package com.jsp.sagar_shopee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.sagar_shopee.exception.CategoryAlreadyExist;
import com.jsp.sagar_shopee.exception.ResourseNotFoundException;
import com.jsp.sagar_shopee.model.Category;
import com.jsp.sagar_shopee.repo.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public Category getCategoryById(long id) {
		// TODO Auto-generated method stub
		return categoryRepo.findById(id).orElseThrow(() -> new ResourseNotFoundException("Resourse Not Found !"));
	}

	@Override
	public Category getCategoryByName(String name) {
		// TODO Auto-generated method stub
		return categoryRepo.findCategoryByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepo.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		return Optional.of(category).filter(c -> !categoryRepo.existByName(c.getName())).map(categoryRepo::save)
				.orElseThrow(() -> new CategoryAlreadyExist(category.getName() + " : already Exist"));
	}

	@Override
	public Category updatCategory(Category category, long id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
			oldCategory.setName(category.getName());
			return categoryRepo.save(oldCategory);
		}).orElseThrow(() -> new ResourseNotFoundException("Resourse not found"));
	}

	@Override
	public void deleteCategoryById(long id) {
		// TODO Auto-generated method stub
		categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete, () -> {
			throw new ResourseNotFoundException("Resourse Not Found !");
		});
	}

}
