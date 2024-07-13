package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.Apiresponse;
import com.blog.payloads.CategoryDto;
import com.blog.payloads.UserDto;
import com.blog.services.CategoryService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/catagories")
public class Categorycontroller {
	@Autowired
	private CategoryService categoryservice;
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategory= this.categoryservice.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		CategoryDto updatecategory = this.categoryservice.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updatecategory);
		
	}
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<Apiresponse> deleteCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		 this.categoryservice.deleteCategory(categoryId);
		return new ResponseEntity<Apiresponse>(new Apiresponse("category deleted successfully",true),HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllcategories() {
		return ResponseEntity.ok(this.categoryservice.getcategories());
	}
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSinglecategory(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(this.categoryservice.getcategory(categoryId));
	}
}
