package com.blog.controllers;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.entities.Post;
import com.blog.payloads.Apiresponse;
import com.blog.payloads.CategoryDto;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/")
public class PostController {
	@Autowired
	private PostService postservice;
	@Autowired
	private FileService fileservice;
	@Value("${project.image}")
	private String path;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){
		
		PostDto createPost= this.postservice.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		
		List<PostDto> posts =this.postservice.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	//get by category
		@GetMapping("/category/{categoryId}/posts")
		public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
			
			List<PostDto> posts =this.postservice.getPostsByCategory(categoryId);
			return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
			
		}
		
		//get by postid
		@GetMapping("/post/{postId}/posts")
		public ResponseEntity<PostDto> getPost(@RequestBody PostDto postDto,@PathVariable Integer postId){
			PostDto getPost=this.postservice.getPostById(postId);
			return new ResponseEntity<PostDto>(getPost,HttpStatus.OK);
		}
		
		//get all posts for pageing 
		@GetMapping("/posts")
		public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
				@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
				@RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
				@RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir) {
			
			PostResponse getallpost = this.postservice.getallpost(pageNumber,pageSize,sortBy,sortDir);
			return new ResponseEntity<PostResponse>(getallpost,HttpStatus.OK);
		}
		//update post
		@PutMapping("/{postId}/posts")
		public ResponseEntity<PostDto> updatepost(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId){
			PostDto updatepost = this.postservice.updatePost(postDto, postId);
			return ResponseEntity.ok(updatepost);
			
		}
		@DeleteMapping("/{postId}/posts")
		public ResponseEntity<Apiresponse> deletePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
			 this.postservice.deletePost(postId);
			return new ResponseEntity<Apiresponse>(new Apiresponse("post deleted successfully",true),HttpStatus.OK);
		}
		//search
		@GetMapping("/posts/search/{keyword}")
		public ResponseEntity<List<PostDto>> searchPostBytitle(@PathVariable String keyword){
			List<PostDto> result=this.postservice.searchPosts(keyword);
			return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
			
		}
		
		//post image upload
		@PostMapping("/posts/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadImage(
				@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException{
			PostDto postDto = this.postservice.getPostById(postId);
			String fileName = this.fileservice.UploadImage(path, image);
			
			postDto.setImagename(fileName);
			PostDto updatePost = this.postservice.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		}
		
		//method to serve files
		
		@GetMapping(value="/posts/image/{imageName}",produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
		public void downloadIamge(@PathVariable ("imageName") String imageName,HttpServletResponse response) throws IOException {
			InputStream resource =this.fileservice.getResource(path, imageName);
			response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
			
			org.springframework.util.StreamUtils.copy(resource,response.getOutputStream());
		}
		
		
}
