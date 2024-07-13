package com.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
 private PostRepository postRepo;
 @Autowired
 private CommentRepository commentRepo;
 
 @Autowired
 private ModelMapper modelmapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		Comment map = this.modelmapper.map(commentDto, Comment.class);
		map.setPost(post);
		Comment savedcomment=this.commentRepo.save(map);
		return this.modelmapper.map(savedcomment, CommentDto.class) ;
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "comment id", commentId));
		this.commentRepo.delete(com);
	}

}
