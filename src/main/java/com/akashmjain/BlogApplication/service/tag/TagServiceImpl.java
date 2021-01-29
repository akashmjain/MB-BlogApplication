package com.akashmjain.BlogApplication.service.tag;

import com.akashmjain.BlogApplication.dao.TagRepository;
import com.akashmjain.BlogApplication.enitity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(int theId) {
        Optional<Tag> optional = tagRepository.findById(theId);
        Tag tag = null;
        if(optional.isPresent()) {
            tag = optional.get();
        } else {
            //TODO: Here change this with exception later
            System.out.println("Error from Tag Service ");
        }
        return tag;
    }

    @Override
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public void deleteById(int theId) {
        tagRepository.deleteById(theId);
    }
}
