package com.blog.services.impl;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repository.CategoryRepository;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import com.blog.services.PostService;
@Service
public class PostServiceImpl implements PostService {
   @Autowired
	private PostRepository postRepo;
   @Autowired
	private ModelMapper modelMapper;
   @Autowired
   private UserRepository userRepo;
   @Autowired
   private CategoryRepository categoryrepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "Userid",userId));
		Category category=this.categoryrepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("category", "categoryid",categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post cat = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("post", "postId", postId));
		cat.setTitle(postDto.getTitle());
		cat.setContent(postDto.getContent());
		cat.setImageName(postDto.getImagename());
		
		
		Post updatecat = this.postRepo.save(cat);
		return this.modelMapper.map(updatecat, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post cat = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "postId", postId));
		this.postRepo.delete(cat);

	}

	@Override
	public PostResponse getallpost(Integer pagenumber,Integer pagesize,String sortBy,String sortDir) {
		org.springframework.data.domain.Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
		    sort = org.springframework.data.domain.Sort.by(sortBy).ascending();
		} else {
		    sort = org.springframework.data.domain.Sort.by(sortBy).descending();
		}
		
		Pageable p=PageRequest.of(pagenumber, pagesize, sort);
		Page<Post> post = this.postRepo.findAll(p);
		List<Post> all = post.getContent();
		List<PostDto> collect = all.stream().map((cat)->this.modelMapper.map(cat,PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(collect);
		postResponse.setPageNumber(post.getNumber());
		postResponse.setPageSize(post.getSize());
		postResponse.setTotalElements(post.getTotalElements());
		postResponse.setTotalPages(post.getTotalPages());
		postResponse.setLastpage(post.isLast());
		return postResponse ;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
		return this.modelMapper.map(post,PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat=this.categoryrepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> collect = posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User cat=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserId", userId));
		List<Post> posts = this.postRepo.findByUser(cat);
		List<PostDto> collect = posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> collect = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class )).collect(Collectors.toList());
		return collect;
	}

}
