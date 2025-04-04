package com.Springboot_blog_webapp.BlogApplication.service;

import com.Springboot_blog_webapp.BlogApplication.dto.commentDto;

import java.util.List;

public interface CommentService {

    void createComment(String postUrl, commentDto commentDto);

    List<commentDto> findAllComments();

    void deleteComment(Long commentId);
}
