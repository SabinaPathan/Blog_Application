package com.Springboot_blog_webapp.BlogApplication.service.impl;

import com.Springboot_blog_webapp.BlogApplication.dto.PostDto;
import com.Springboot_blog_webapp.BlogApplication.entity.Post;
import com.Springboot_blog_webapp.BlogApplication.entity.User;
import com.Springboot_blog_webapp.BlogApplication.mapper.PostMapper;
import com.Springboot_blog_webapp.BlogApplication.repository.PostRepository;
import com.Springboot_blog_webapp.BlogApplication.repository.UserRepository;
import com.Springboot_blog_webapp.BlogApplication.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    @Override
    public List<PostDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
               return posts.stream() .map( PostMapper::mapToPostDto)
                        .collect(Collectors.toList());

    }

    @Override
    public void createPost(PostDto postDto, String username) {
        User creator = userRepository.findByEmail(username);
        if (creator == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        Post post = PostMapper.mapToPost(postDto);
        post.setCreatedBy(creator);
        postRepository.save(post);
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return PostMapper.mapToPostDto(post);
    }

    @Override
    public void updatePost(PostDto postDto) {
        Post post = PostMapper.mapToPost(postDto);
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto findPostByUrl(String postUrl) {

        return postRepository.findByUrl(postUrl)
                .map(PostMapper::mapToPostDto)
                .orElse(null);
    }

    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> posts = postRepository.searchPosts(query);
        return posts.stream()
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PostDto> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(PostMapper::mapToPostDto);
    }


}
