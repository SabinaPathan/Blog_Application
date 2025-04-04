package com.Springboot_blog_webapp.BlogApplication.repository;

import com.Springboot_blog_webapp.BlogApplication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
