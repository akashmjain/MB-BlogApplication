package com.akashmjain.BlogApplication.dao;

import com.akashmjain.BlogApplication.enitity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
