package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.dao.UserRepository;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.TagEntity;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class FilterController {
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/filter")
    public String getFilteredData(@RequestParam(value = "tagId", required = false) List<Integer> tagIds, @RequestParam(value = "authorId", required = false) List<Integer> authorIds,Model model) {
        List<PostEntity> posts = null;
        System.out.println("LOG :: AUTHOR ID " + authorIds);
        System.out.println("LOG :: TAG ID " + tagIds);
        if(authorIds != null) posts = userService.getPostsByUserIdList(authorIds);
        if(tagIds != null) posts = tagService.getPostsByTagIdList(tagIds, posts);
        System.out.println("LOG :: POSTS " + posts);
        model.addAttribute("data", posts);
        return "test";
    }

//    @RequestMapping(value = "/filter", params = "tagId")
//    public String getPostByTags(@RequestParam("tagId")int tagId, @RequestParam(name = "page", required = false, defaultValue = "0") int pageNo, Model model) {
//        Pageable pageable = PageRequest.of(pageNo, 3);
//        TagEntity tagEntity = tagService.findById(tagId);
//        Long start = pageable.getOffset();
//        Long end =(start + pageable.getPageSize()) > tagEntity.getPosts().size() ? tagEntity.getPosts().size() : (start + pageable.getPageSize());
//        Page<PostEntity> pages = new PageImpl<PostEntity>(tagEntity.getPosts().subList(start.intValue(), end.intValue()), pageable, tagEntity.getPosts().size());
//        model.addAttribute("totalPages",pages.getTotalPages());
//        model.addAttribute("posts", pages.getContent());
//        model.addAttribute("pathTo", "/filter?tagId="+tagId);
//        return "index";
//    }
}
