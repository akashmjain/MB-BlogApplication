package com.akashmjain.BlogApplication.service.tag;

import com.akashmjain.BlogApplication.enitity.TagEntity;

import java.util.List;

public interface TagService {
    public List<TagEntity> findAll();

    public TagEntity findById(int theId);

    public void save(TagEntity tagEntity);

    public void deleteById(int theId);
}