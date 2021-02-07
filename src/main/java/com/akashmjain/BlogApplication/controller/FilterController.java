package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class FilterController {
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private PostService postService;

    @RequestMapping("/")
    public String getFilteredData(HttpServletRequest httpServletRequest,
                                  @ModelAttribute("pathTo") String pathTo,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") int pageNo,
                                  @RequestParam(value = "search", required = false) String search,
                                  @RequestParam(value = "tagId", required = false) List<Integer> tagIds,
                                  @RequestParam(value = "authorId", required = false) List<Integer> authorIds,
                                  @RequestParam(value = "order", required = false, defaultValue = "asc") String sortOrder,
                                  Model model) {
        String urlData = "?";
        List<PostEntity> posts = postService.findAll();
        if (search != null) {
            posts = getSearchResult(search);
            urlData += "search="+search;
        }
        if (authorIds != null) {
            posts = userService.getPostsByUserIdList(authorIds);
            for(int id : authorIds) {
                urlData += "&authorId="+id;
            }
        }
        if (tagIds != null) {
            posts = tagService.getPostsByTagIdList(tagIds, posts);
            for(int id : tagIds) {
                urlData += "&tagId="+id;
            }
        }
        Set<PostEntity> set = new LinkedHashSet<>(posts);
        posts.clear();
        posts.addAll(set);
        if (sortOrder.toLowerCase(Locale.ROOT).equals("asc"))
            posts.sort(new Comparator<PostEntity>() {
                @Override
                public int compare(PostEntity o2, PostEntity o1) {
                    return o1.getUpdatedAt().compareTo(o2.getUpdatedAt());
                }
            });
        else if (sortOrder.toLowerCase(Locale.ROOT).equals("dsc"))
            posts.sort(new Comparator<PostEntity>() {
                @Override
                public int compare(PostEntity o1, PostEntity o2) {
                    return o1.getUpdatedAt().compareTo(o2.getUpdatedAt());
                }
            });
        Pageable pageable = PageRequest.of(pageNo, 2);
        long start = pageable.getOffset();
        long end =(start + pageable.getPageSize()) > posts.size() ? posts.size() : (start + pageable.getPageSize());
        Page<PostEntity> pages = new PageImpl<PostEntity>(posts.subList((int)start, (int)end), pageable, posts.size());

        model.addAttribute("totalPages",pages.getTotalPages());
        model.addAttribute("posts", pages.getContent());
        model.addAttribute("pathTo", urlData);
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allTags", tagService.findAll());
//        model.addAttribute("posts", posts);
        return "index";
    }

    private List<PostEntity> getSearchResult(String search) {
        List<PostEntity> postEntities = new ArrayList<>();
        postEntities.addAll(postService.getPostsBySearchString(search));
        postEntities.addAll(userService.getPostsByUserName(search));
        postEntities.addAll(tagService.getPostsByTagName(search));

        return postEntities;
    }
}
