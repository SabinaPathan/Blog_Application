package com.Springboot_blog_webapp.BlogApplication.service;

import com.Springboot_blog_webapp.BlogApplication.dto.RegistrationDto;
import com.Springboot_blog_webapp.BlogApplication.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public interface UserService {

    void saveUser(RegistrationDto registrationDto);

    User findByEmail(String email);

}
