package com.Springboot_blog_webapp.BlogApplication.repository;

import com.Springboot_blog_webapp.BlogApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
