package com.Springboot_blog_webapp.BlogApplication.service.impl;

import com.Springboot_blog_webapp.BlogApplication.dto.RegistrationDto;
import com.Springboot_blog_webapp.BlogApplication.entity.Role;
import com.Springboot_blog_webapp.BlogApplication.entity.User;
import com.Springboot_blog_webapp.BlogApplication.repository.RoleRepository;
import com.Springboot_blog_webapp.BlogApplication.repository.UserRepository;
import com.Springboot_blog_webapp.BlogApplication.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void saveUser(RegistrationDto registrationDto) {

        System.out.println("Raw password: " + registrationDto.getPassword());
        String encoded = passwordEncoder.encode(registrationDto.getPassword());
        System.out.println("Encoded password: " + encoded);

        User user = new User();
        user.setName(registrationDto.getFirstName() +" "+ registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());

        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_GUEST");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Add this to your UserServiceImpl or a configuration class
    @PostConstruct
    public void initRoles() {
        if (roleRepository.findByName("ROLE_GUEST") == null) {
            Role guestRole = new Role();
            guestRole.setName("ROLE_GUEST");
            roleRepository.save(guestRole);
        }
    }

}
