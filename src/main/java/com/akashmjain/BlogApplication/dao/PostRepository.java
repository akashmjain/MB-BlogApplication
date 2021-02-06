package com.akashmjain.BlogApplication.dao;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    public List<PostEntity> findByTitleContaining (String title);
    public List<PostEntity> findByContentContaining (String title);
    public List<PostEntity> findByExcerptContaining (String title);
}
