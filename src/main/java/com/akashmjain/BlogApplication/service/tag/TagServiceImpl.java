package com.akashmjain.BlogApplication.service.tag;

import com.akashmjain.BlogApplication.dao.TagRepository;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Override
    public List<TagEntity> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public TagEntity findById(int theId) {
        Optional<TagEntity> optional = tagRepository.findById(theId);
        TagEntity tagEntity = null;
        if (optional.isPresent()) {
            tagEntity = optional.get();
        } else {
            //TODO: Here change this with exception later
            System.out.println("Error from Tag Service ");
        }
        return tagEntity;
    }

    @Override
    public void save(TagEntity tagEntity) {
        tagRepository.save(tagEntity);
    }

    @Override
    public void deleteById(int theId) {
        tagRepository.deleteById(theId);
    }

    @Override
    public List<TagEntity> stringToTag(String tagSting) {
        List<TagEntity> tagList = new ArrayList<>();
        String[] tagNameArr = tagSting.toLowerCase(Locale.ROOT).replaceAll("\\s","").split(",");
        for (String tagName : tagNameArr) {
            TagEntity tagEntity = tagRepository.findByName(tagName);
            if (tagEntity == null) {
                tagEntity = new TagEntity();
                tagEntity.setName(tagName);
                tagEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                tagEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                this.save(tagEntity);
            }
            tagList.add(tagEntity);
        }
        return tagList;
    }

    @Override
    public List<PostEntity> getPostsByTagIdList(List<Integer> tagIds, List<PostEntity> postEntities) {
        List<PostEntity> posts = new ArrayList<>();
        if (postEntities == null) {
            for (int tagId : tagIds) {
                posts.addAll(this.findById(tagId).getPosts());
            }
        } else {
            for (PostEntity postEntity : postEntities) {
                for (int tagId : tagIds) {
                    if (postEntity.getTags().contains(this.findById(tagId))) {
                        posts.add(postEntity);
                    }
                }
            }
        }
        // to clear any duplicates
        Set<PostEntity> set = new LinkedHashSet<>(posts);
        posts.clear();
        posts.addAll(set);
        return posts;
    }
}
