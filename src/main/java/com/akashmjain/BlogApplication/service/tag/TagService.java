package com.akashmjain.BlogApplication.service.tag;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.TagEntity;

import java.util.List;

public interface TagService {
    public List<TagEntity> findAll();

    public TagEntity findById(int theId);

    public void save(TagEntity tagEntity);

    public void deleteById(int theId);

    public List<TagEntity> stringToTag(String tagSting);

    public List<PostEntity> getPostsByTagIdList(List<Integer> tagIds, List<PostEntity> postEntities);

    public List<PostEntity> getPostsByTagName(String query);
}