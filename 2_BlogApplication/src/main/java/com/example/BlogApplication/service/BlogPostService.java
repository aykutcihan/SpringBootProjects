package com.example.BlogApplication.service;

import com.example.BlogApplication.entity.BlogPost;
import com.example.BlogApplication.payload.BlogPostRequest;
import com.example.BlogApplication.payload.BlogPostResponse;
import com.example.BlogApplication.payload.ResponseMessage;
import com.example.BlogApplication.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public ResponseMessage<BlogPostResponse> createPost(BlogPostRequest blogPostRequest) {
        // BlogPost entity'sini BlogPostRequest'ten oluştur
        BlogPost blogPost = new BlogPost();
        BeanUtils.copyProperties(blogPostRequest, blogPost);

        BlogPost savedPost = blogPostRepository.save(blogPost);

        BlogPostResponse blogPostResponse = new BlogPostResponse();
        BeanUtils.copyProperties(savedPost, blogPostResponse);

        return ResponseMessage.<BlogPostResponse>builder()
                .message("Blog post successfully created.")
                .httpStatus(HttpStatus.CREATED)
                .object(blogPostResponse)
                .build();


    }

    public ResponseMessage<BlogPostResponse> getPostById(Long id) {
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(id);

        if (blogPostOptional.isPresent()) {
            BlogPostResponse blogPostResponse = new BlogPostResponse();
            BeanUtils.copyProperties(blogPostOptional.get(), blogPostResponse);

            return ResponseMessage.<BlogPostResponse>builder()
                    .message("Blog post found.")
                    .httpStatus(HttpStatus.OK)
                    .object(blogPostResponse)
                    .build();
        } else {
            return ResponseMessage.<BlogPostResponse>builder()
                    .message("Blog post not found.")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .object(null)
                    .build();
        }
    }

    public List<BlogPostResponse> getAllBlogPost() {
        return blogPostRepository.findAll()
                .stream()
                .map(blogPost -> {
                    BlogPostResponse response = new BlogPostResponse();
                    BeanUtils.copyProperties(blogPost, response);
                    return response;
                })
                .collect(Collectors.toList());
    }


    public ResponseMessage<BlogPostResponse> updateBlogPost(Long id, BlogPostRequest blogPostRequest) {
        Optional<BlogPost> existingBlogPost = blogPostRepository.findById(id);

        if (existingBlogPost.isPresent()) {
            BlogPost blogPostToUpdate = existingBlogPost.get();
            // BlogPostRequest'ten gelen bilgileri kullanarak blog postunu güncelle
            BeanUtils.copyProperties(blogPostRequest, blogPostToUpdate);

            BlogPost updatedBlogPost = blogPostRepository.save(blogPostToUpdate);

            BlogPostResponse blogPostResponse = new BlogPostResponse();
            BeanUtils.copyProperties(updatedBlogPost, blogPostResponse);

            return ResponseMessage.<BlogPostResponse>builder()
                    .message("Blog post successfully updated.")
                    .httpStatus(HttpStatus.OK)
                    .object(blogPostResponse)
                    .build();

        } else {
            return ResponseMessage.<BlogPostResponse>builder()
                    .message("Blog post not found.")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .object(null)
                    .build();
        }
    }

    public ResponseMessage<?> deleteBlogPost(Long id) {
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(id);

        if (blogPostOptional.isPresent()) {
            blogPostRepository.deleteById(id);

            return ResponseMessage.builder()
                    .message("BlogPost deleted")
                    .httpStatus(HttpStatus.OK)
                    .build();

        } else {
            throw new EntityNotFoundException("Blog post not found with id: " + id);
        }



    }

}
