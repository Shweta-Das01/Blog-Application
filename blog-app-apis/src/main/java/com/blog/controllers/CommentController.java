package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.Apiresponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api/")

public class CommentController {
	@Autowired
	private CommentService  commentService;
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){
		
		CommentDto comment = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(comment,HttpStatus.CREATED);
		
	}
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Apiresponse> deleteComment(@PathVariable Integer commentId){
		
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<Apiresponse>(new Apiresponse("comment deleted!!!!!!!!!!!!!!",true),HttpStatus.OK);
		
	}
}
