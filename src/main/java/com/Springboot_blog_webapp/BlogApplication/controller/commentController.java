package com.Springboot_blog_webapp.BlogApplication.controller;

import com.Springboot_blog_webapp.BlogApplication.dto.PostDto;
import com.Springboot_blog_webapp.BlogApplication.dto.commentDto;
import com.Springboot_blog_webapp.BlogApplication.service.CommentService;
import com.Springboot_blog_webapp.BlogApplication.service.PostService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class commentController {

    private CommentService commentService;

    private PostService postService;

    public commentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    //handler method for submitting comment
    @PostMapping("/{postUrl}/comments")

    public String createComment(@PathVariable("postUrl") String postUrl, @Valid @ModelAttribute("comment") commentDto Dto, BindingResult bindingResult, Model model) {

        PostDto post = postService.findPostByUrl(postUrl);

        if(bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            model.addAttribute("comment", Dto);
            return "blog/blog_post";
        }
        System.out.println("Creating comment for post URL: " + postUrl); // Debugging
        commentService.createComment(postUrl, Dto);
        return "redirect:/post/" + postUrl;
    }


}
