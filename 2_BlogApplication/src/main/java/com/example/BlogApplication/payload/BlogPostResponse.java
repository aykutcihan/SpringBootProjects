package com.example.BlogApplication.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BlogPostResponse {

    private Long id;
    private String title;
    private String content;
}
