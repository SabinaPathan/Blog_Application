package com.Springboot_blog_webapp.BlogApplication.repository;

import com.Springboot_blog_webapp.BlogApplication.dto.PostDto;
import com.Springboot_blog_webapp.BlogApplication.entity.Post;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {


    Optional<Post> findByUrl(String url);

    @Query("SELECT p FROM Post p WHERE p.title LIKE CONCAT('%', :query, '%') OR p.content LIKE CONCAT('%', :query, '%')")
    List<Post> searchPosts(String query);






}
