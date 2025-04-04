package com.Springboot_blog_webapp.BlogApplication.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    @NotEmpty(message = "Post title should not be empty")
    private String title;


    private String url;


    @NotEmpty(message = "Post content should not be empty")
    @Size(max = 10000, message = "Content too long")
    private String content;

    @NotEmpty(message = "Post short description should not be empty")
    @Size(max = 1000, message = "Short description must be less than 1000 characters")
    private String shortDescription;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private Set<commentDto> comments;

}
