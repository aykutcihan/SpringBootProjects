package com.example.BlogApplication.repository;

import com.example.BlogApplication.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository <BlogPost, Long> {

}
