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
    public String getFilteredData(@RequestParam(value = "search", required = false) String search,
                                  @RequestParam(value = "tagId", required = false) List<Integer> tagIds,
                                  @RequestParam(value = "authorId", required = false) List<Integer> authorIds,
                                  @RequestParam(value = "order", required = false, defaultValue = "asc") String sortOrder,
                                  Model model) {
        System.out.println(sortOrder);
        List<PostEntity> posts = new ArrayList<>();
        if (search != null) posts = getSearchResult(search);
        if (authorIds != null) posts = userService.getPostsByUserIdList(authorIds);
        if (tagIds != null) posts = tagService.getPostsByTagIdList(tagIds, posts);
        Set<PostEntity> set = new LinkedHashSet<>(posts);
        posts.clear();
        posts.addAll(set);
        posts.sort(new Comparator<PostEntity>() {
            @Override
            public int compare(PostEntity o2, PostEntity o1) {
                return o1.getUpdatedAt().compareTo(o2.getUpdatedAt());
            }
        });
        model.addAttribute("data", posts);
        return "test";
    }

    private List<PostEntity> getSearchResult(String search) {
        List<PostEntity> postEntities = new ArrayList<>();
        postEntities.addAll(postService.getPostsBySearchString(search));
        postEntities.addAll(userService.getPostsByUserName(search));
        postEntities.addAll(tagService.getPostsByTagName(search));

        return postEntities;
    }
}
