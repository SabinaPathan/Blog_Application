package com.Springboot_blog_webapp.BlogApplication.mapper;

import com.Springboot_blog_webapp.BlogApplication.dto.commentDto;
import com.Springboot_blog_webapp.BlogApplication.entity.Comment;

public class CommentMapper {

    //convert  Comment entity to commentDto

    public static commentDto mapTocommentDto(Comment comment) {
        return commentDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .content(comment.getContent())
                .createdOn(comment.getCreatedOn())
                .updatedOn(comment.getUpdatedOn())
                .build();
    }

    //convert commentDto to Comment entity

    public static Comment mapToComment(commentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .name(commentDto.getName())
                .email(commentDto.getEmail())
                .content(commentDto.getContent())
                .createdOn(commentDto.getCreatedOn())
                .updatedOn(commentDto.getUpdatedOn())
                .build();
    }


}
