package com.Springboot_blog_webapp.BlogApplication.controller;


import com.Springboot_blog_webapp.BlogApplication.dto.RegistrationDto;
import com.Springboot_blog_webapp.BlogApplication.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //handle method to handle login page request
    @GetMapping("/login")
    public String LoginPage(){

    return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        RegistrationDto registrationDto = new RegistrationDto();
        model.addAttribute("user", registrationDto);
        return "Sign-up";
    }

    //handler method to handle user submit request
    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult bindingResult,
                           Model model) {

        // Check if email exists
        if(userService.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "email.duplicate", "There is already an account registered with this email");
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "Sign-up";
        }

        userService.saveUser(user);
        return "redirect:/register?success";


    }


}
