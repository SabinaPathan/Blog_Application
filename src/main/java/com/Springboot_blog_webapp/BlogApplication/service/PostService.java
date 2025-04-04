package com.Springboot_blog_webapp.BlogApplication.service;

import com.Springboot_blog_webapp.BlogApplication.dto.PostDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    List<PostDto> findAllPosts();

    void createPost(PostDto post);

    PostDto findPostById(Long postId);

    void updatePost(PostDto postDto);

    void deletePost(Long postId);

    PostDto findPostByUrl(String postUrl);

    List<PostDto> searchPosts(String query);

    Page<PostDto> findPaginated(int page, int size);
}
