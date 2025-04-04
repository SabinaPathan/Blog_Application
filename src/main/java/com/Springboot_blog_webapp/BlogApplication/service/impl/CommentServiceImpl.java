package com.Springboot_blog_webapp.BlogApplication.service.impl;

import com.Springboot_blog_webapp.BlogApplication.dto.commentDto;
import com.Springboot_blog_webapp.BlogApplication.entity.Comment;
import com.Springboot_blog_webapp.BlogApplication.entity.Post;
import com.Springboot_blog_webapp.BlogApplication.mapper.CommentMapper;
import com.Springboot_blog_webapp.BlogApplication.repository.PostRepository;
import com.Springboot_blog_webapp.BlogApplication.repository.commentRepository;
import com.Springboot_blog_webapp.BlogApplication.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private commentRepository commentrepository;
    private PostRepository postRepository;

    public CommentServiceImpl(commentRepository commentrepository, PostRepository postRepository) {
        this.commentrepository = commentrepository;
        this.postRepository = postRepository;
    }

    @Override
    public void createComment(String postUrl, commentDto commentDto) {
        Post post = postRepository.findByUrl(postUrl).get();

        Comment comment = CommentMapper.mapToComment(commentDto);
        comment.setPost(post);
        commentrepository.save(comment);

    }

    @Override
    public List<commentDto> findAllComments() {
        List<Comment> comments = commentrepository.findAll();
        return comments.stream().map(CommentMapper::mapTocommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentrepository.deleteById(commentId);
    }
}
