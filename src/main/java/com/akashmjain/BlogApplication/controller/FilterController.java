package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.dao.UserRepository;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.TagEntity;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import com.akashmjain.BlogApplication.service.post.PostService;
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

import java.util.*;

@Controller
public class FilterController {
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private PostService postService;

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
    @RequestMapping("/query")
    public String getQueryData(@RequestParam("search") String search,Model model) {
        List<PostEntity> postEntities = new ArrayList<>();
        postEntities.addAll(postService.getPostsBySearchString(search));
        postEntities.addAll(userService.getPostsByUserName(search));
        postEntities.addAll(tagService.getPostsByTagName(search));
        Set<PostEntity> set = new LinkedHashSet<>(postEntities);
        postEntities.clear();
        postEntities.addAll(set);
        System.out.println("DATA :- " + postEntities);
        model.addAttribute("data", postEntities);
        return "test";
    }
}
