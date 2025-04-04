package com.Springboot_blog_webapp.BlogApplication.repository;

import com.Springboot_blog_webapp.BlogApplication.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface commentRepository extends JpaRepository <Comment, Long> {

}
