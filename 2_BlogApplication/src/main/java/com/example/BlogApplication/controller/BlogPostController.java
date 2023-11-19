package com.example.BlogApplication.controller;

import com.example.BlogApplication.entity.BlogPost;
import com.example.BlogApplication.payload.BlogPostRequest;
import com.example.BlogApplication.payload.BlogPostResponse;
import com.example.BlogApplication.payload.ResponseMessage;
import com.example.BlogApplication.service.BlogPostService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/blogposts")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PostMapping("/save")
    public ResponseMessage<BlogPostResponse> createPost(@RequestBody @Valid BlogPostRequest blogPostRequest) {
        return blogPostService.createPost(blogPostRequest);
    }

    @GetMapping("/{id}")
    public ResponseMessage<BlogPostResponse> getBlogPostById(@PathVariable Long id) {
        return blogPostService.getPostById(id);
    }

    @GetMapping("/getAll")
    public List<BlogPostResponse> getAllBlogPost(){
        return blogPostService.getAllBlogPost();
    }

    @PutMapping("/update/{id}")
    public ResponseMessage<BlogPostResponse> updateBlogPost(@PathVariable Long id,
                                                            @RequestBody @Valid BlogPostRequest blogPostRequest) {
        return blogPostService.updateBlogPost(id, blogPostRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage<?> deleteBlogPost(@PathVariable Long id) {

        return  blogPostService.deleteBlogPost(id);
    }







}
