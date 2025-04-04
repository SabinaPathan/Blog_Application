package com.Springboot_blog_webapp.BlogApplication.controller;

import com.Springboot_blog_webapp.BlogApplication.dto.PostDto;
import com.Springboot_blog_webapp.BlogApplication.dto.commentDto;
import com.Springboot_blog_webapp.BlogApplication.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogController {

    private final PostService postService;

    public BlogController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String viewBlogPosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "6") int size,
            Model model) {

        Page<PostDto> postPage = postService.findPaginated(page, size);

        model.addAttribute("postResponse", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());

        return "blog/view_posts";
    }

    @GetMapping("/post/{postUrl}")
    public String showPublicPost(@PathVariable("postUrl") String postUrl, Model model) {
        System.out.println("Fetching post with URL: " + postUrl); // Debugging
        PostDto post = postService.findPostByUrl(postUrl);

        if (post == null) {
            System.out.println("Post not found! Redirecting to home.");
            return "redirect:/";
        }

        commentDto comment = new commentDto();

        model.addAttribute("post", post);

        model.addAttribute("comment", comment);
        return "blog/blog_post";
    }

    //handler method for searching posts
    @GetMapping("/page/search")
    public String searchPosts(@RequestParam("query") String query, Model model) {

           List<PostDto> postResponse = postService.searchPosts(query);
        model.addAttribute("postResponse", postResponse);

        return "blog/view_posts";
    }

}