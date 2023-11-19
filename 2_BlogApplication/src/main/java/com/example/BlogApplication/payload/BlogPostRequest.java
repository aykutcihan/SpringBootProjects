package com.example.BlogApplication.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
public class BlogPostRequest {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title length must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;
}
