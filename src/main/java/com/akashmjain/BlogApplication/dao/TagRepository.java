package com.akashmjain.BlogApplication.dao;

import com.akashmjain.BlogApplication.enitity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {
    public TagEntity findByName(String name);
}
