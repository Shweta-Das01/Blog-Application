package com.blog.services;

import java.util.List;

import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
    //create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all posts
	PostResponse getallpost(Integer pagenumber,Integer pagesize,String sortBy,String sortDir);
	//get single post
		PostDto getPostById(Integer postId);
		//get post by category
		List<PostDto> getPostsByCategory(Integer categoryId);
		//get post by user
		List<PostDto> getPostsByUser(Integer userId);
		
		//getSearch post
		List<PostDto> searchPosts(String keyword);
}
