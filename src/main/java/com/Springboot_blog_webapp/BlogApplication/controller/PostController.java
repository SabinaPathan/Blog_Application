package com.Springboot_blog_webapp.BlogApplication.controller;

import com.Springboot_blog_webapp.BlogApplication.dto.PostDto;
import com.Springboot_blog_webapp.BlogApplication.dto.commentDto;
import com.Springboot_blog_webapp.BlogApplication.service.CommentService;
import com.Springboot_blog_webapp.BlogApplication.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
public class PostController {

    private PostService postService;

    private CommentService commentService;

    public PostController(PostService postService,CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    //create handler method ,Get request and return model and view
    @GetMapping("/admin/posts")
    public String listPosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "6") int size,
            Model model) {

        Page<PostDto> postsPage = postService.findPaginated(page, size);

        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        model.addAttribute("totalItems", postsPage.getTotalElements());

        return "/admin/posts";
    }

    //create handler method for add new post
    @GetMapping("/admin/posts/newpost")
    public String createPost(Model model) {
        PostDto post = new PostDto();
        model.addAttribute("post", post);
        return "/admin/create_post";
    }

    //create handler method for saving post
    @PostMapping("/admin/posts/save")
    public String createPost(@Valid @ModelAttribute("post") PostDto post, BindingResult bindingResult, Model model, Principal principal) {
        // Debugging output
        System.out.println("Received post: " + post);
        System.out.println("Title: " + post.getTitle());
        System.out.println("Content: " + post.getContent());
        System.out.println("Short Description: " + post.getShortDescription());

        // Check if there are validation errors
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors:");
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
            model.addAttribute("post", post);
            return "/admin/create_post";
        }

        try {
            // Check if title is not null or empty before setting URL
            if (post.getTitle() == null || post.getTitle().trim().isEmpty()) {
                model.addAttribute("error", "Post title cannot be empty");
                return "/admin/create_post";
            }

            // Set the URL based on the title
            String url = getUrl(post.getTitle());

            // Ensure the URL is not empty
            if (url.isEmpty()) {
                url = "post-" + System.currentTimeMillis(); // Fallback URL with timestamp
            }

            post.setUrl(url);
            System.out.println("URL set to: " + post.getUrl());

            // Create the post
            postService.createPost(post,principal.getName());
            System.out.println("Post created successfully");
            return "redirect:/admin/posts";
        } catch (Exception e) {
            System.out.println("Error creating post: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("post", post);
            model.addAttribute("error", "Error saving post: " + e.getMessage());
            return "/admin/create_post";
        }
    }

    //create handler method for edit post
    @GetMapping("/admin/posts/{postId}/edit")
    public String editPost(@PathVariable("postId") Long postId, Model model) {
        PostDto post = postService.findPostById(postId);
        model.addAttribute("post", post);
        return "/admin/edit_post";
    }

    //create handler method for updated post
     @PostMapping("/admin/posts/{postId}")
     public String updatePost(@PathVariable ("postId") Long postId,
                              @Valid @ModelAttribute("post") PostDto postDto,
                              BindingResult bindingResult, Model model) {

         // Check if there are validation errors
         if (bindingResult.hasErrors()) {

             model.addAttribute("post", postDto);
             return "/admin/edit_post";
         }

         postDto.setId(postId);
         postService.updatePost(postDto);
            return "redirect:/admin/posts";
     }

    //create handler method for delete post
    @GetMapping("/admin/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }

    //handle view post request
    @GetMapping("/admin/posts/view")
    public String viewPost(@RequestParam("url") String postUrl, Model model) {
        PostDto post = postService.findPostByUrl(postUrl);
        if (post == null) {
            // Handle post not found
            return "redirect:/admin/posts";
        }
        model.addAttribute("post", post);
        return "admin/view_post";
    }

    //handle search post request
    @GetMapping("/admin/posts/search")
    public String searchPosts(@RequestParam( value = "query") String query, Model model) {
        List<PostDto> posts = postService.searchPosts(query);
        model.addAttribute("posts", posts);
        return "/admin/posts";
    }




    private static String getUrl(String postTitle) {
        if (postTitle == null || postTitle.trim().isEmpty()) {
            return ""; // Return empty string to handle this case in the controller
        }

        // Convert to lowercase and trim
        String title = postTitle.trim().toLowerCase();
        // Replace spaces with hyphens
        String url = title.replaceAll("\\s+", "-");
        // Replace non-alphanumeric characters with hyphens
        url = url.replaceAll("[^a-zA-Z0-9-]", "-");
        // Replace multiple consecutive hyphens with a single hyphen
        url = url.replaceAll("-+", "-");
        // Remove leading and trailing hyphens
        url = url.replaceAll("^-|-$", "");

        // Ensure we don't return an empty string
        if (url.isEmpty()) {
            return "post-" + System.currentTimeMillis();
        }

        return url;
    }

    //handler for comment page
    @GetMapping("/admin/posts/comments")
    public String postComments(Model model){
    List<commentDto>comments = commentService.findAllComments();
    model.addAttribute("comments",comments);
    return "admin/comments";
    }

   //handler method to delete comment request
   //handler method to delete comment request
   @GetMapping("/admin/posts/comments/{commentId}")
   public String deleteComment(@PathVariable("commentId") Long commentId) {
       commentService.deleteComment(commentId);
       return "redirect:/admin/posts/comments";
   }
}