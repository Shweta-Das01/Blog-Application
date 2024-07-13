package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repository.CategoryRepository;
import com.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
private CategoryRepository categoryRepo;
	@Autowired
	private ModelMapper modelmapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelmapper.map(categoryDto, Category.class);
		Category addcat = this.categoryRepo.save(cat);
		return this.modelmapper.map(addcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("category", "categoryId", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCatagoryDescription(categoryDto.getCatagoryDescription());
		Category updatecat = this.categoryRepo.save(cat);
		return this.modelmapper.map(updatecat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "categoryId", categoryId));
		this.categoryRepo.delete(cat);
		
	}

	@Override
	public CategoryDto getcategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "categoryId", categoryId));
		return this.modelmapper.map(cat,CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getcategories() {

List<Category>all=this.categoryRepo.findAll();
List<CategoryDto> collect = all.stream().map((cat)->this.modelmapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
		return collect;
	}
	

}
