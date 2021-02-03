package com.akashmjain.BlogApplication.service.tag;

import com.akashmjain.BlogApplication.dao.TagRepository;
import com.akashmjain.BlogApplication.enitity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if(optional.isPresent()) {
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
}
