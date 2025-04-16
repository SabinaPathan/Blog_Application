package com.Springboot_blog_webapp.BlogApplication.service.impl;

import com.Springboot_blog_webapp.BlogApplication.dto.RegistrationDto;
import com.Springboot_blog_webapp.BlogApplication.entity.Role;
import com.Springboot_blog_webapp.BlogApplication.entity.User;
import com.Springboot_blog_webapp.BlogApplication.repository.RoleRepository;
import com.Springboot_blog_webapp.BlogApplication.repository.UserRepository;
import com.Springboot_blog_webapp.BlogApplication.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void saveUser(RegistrationDto registrationDto) {
        try {
            ensureRolesExist(); // Ensure roles exist before saving user

            User user = new User();
            user.setName(registrationDto.getFirstName() + " " + registrationDto.getLastName());
            user.setEmail(registrationDto.getEmail());
            user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

            Role role = roleRepository.findByName("ROLE_GUEST");
            if (role == null) {
                System.out.println("ROLE_GUEST not found!");
                // Create the role or handle the error
            }
            user.setRoles(Arrays.asList(role));
            userRepository.save(user);
            System.out.println("User saved successfully: " + user.getEmail());
        } catch (Exception e) {
            System.out.println("Error saving user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private void ensureRolesExist() {
        if (roleRepository.findByName("ROLE_GUEST") == null) {
            Role guestRole = new Role();
            guestRole.setName("ROLE_GUEST");
            roleRepository.save(guestRole);
        }

        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }
    }
}