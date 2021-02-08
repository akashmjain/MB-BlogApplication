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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class FilterController {
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private PostService postService;

    private String urlData;
    @RequestMapping("/")
    public String getFilteredData(@RequestParam(value = "page", required = false, defaultValue = "0") int pageNo,
                                  @RequestParam(value = "search", required = false) String search,
                                  @RequestParam(value = "tagId", required = false) List<Integer> tagIds,
                                  @RequestParam(value = "authorId", required = false) List<Integer> authorIds,
                                  @RequestParam(value = "order", required = false, defaultValue = "asc") String sortOrder,
                                  @RequestParam(value = "startDate", required = false, defaultValue = "asc") String startDate,
                                  @RequestParam(value = "endDate", required = false, defaultValue = "asc") String endDate,
                                  Model model) {
        urlData = "?";
        List<PostEntity> posts;
        if(search == null && tagIds == null && authorIds == null)
            posts = postService.findAll();
        else {
            posts = getFilteredPostEntityList(authorIds, tagIds, search);
        }
        posts = getUniquePostEntityList(posts);
        posts = getPostFilteredByDate(startDate, endDate, posts);
        posts = getSortedPostEntityList(posts, sortOrder);
        posts = getPostFilteredByDate(startDate, endDate, posts);

        Page<PostEntity> page = getPaginatedData(posts, pageNo, 4);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("posts", page.getContent());
        model.addAttribute("pathTo", urlData);
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allTags", tagService.findAll());
        return "index";
    }

    private List<PostEntity> getUniquePostEntityList(List<PostEntity> posts) {
        Set<PostEntity> set = new LinkedHashSet<>(posts);
        posts.clear();
        posts.addAll(set);
        return posts;
    }

    private List<PostEntity> getSortedPostEntityList(List<PostEntity> posts, String sortOrder) {
        List<PostEntity> modifiableList = new ArrayList<PostEntity>(posts);
        if (sortOrder.toLowerCase(Locale.ROOT).equals("dsc"))
            modifiableList.sort((o2, o1) -> o1.getPublishedAt().compareTo(o2.getPublishedAt()));
        else if (sortOrder.toLowerCase(Locale.ROOT).equals("asc")) {
            modifiableList.sort((o1, o2) -> o1.getPublishedAt().compareTo(o2.getPublishedAt()));
        }
        return modifiableList;
    }

    private List<PostEntity> getFilteredPostEntityList(List<Integer> authorIds, List<Integer> tagIds, String search) {
        List<PostEntity> posts = new ArrayList<>();
        if (search != null) {
            posts.addAll(postService.getPostsBySearchString(search));
            posts.addAll(userService.getPostsByUserName(search));
            posts.addAll(tagService.getPostsByTagName(search));
            urlData += "search="+search;
        }
        if (authorIds != null) {
            posts = userService.getPostsByUserIdList(authorIds, posts);
            for(int id : authorIds) {
                urlData += "&authorId="+id;
            }
        }
        if (tagIds != null) {
            posts = tagService.getPostsByTagIdList(tagIds, posts);
            for(int id : tagIds) {
                urlData += "&tagId=" + id;
            }
        }
        return posts;
    }

    private List<PostEntity> getPostFilteredByDate(String startDateString, String endDateString, List<PostEntity> posts) {
        if (posts.isEmpty()) return null;
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
            for (PostEntity post : posts) {
                Date postDate = new Date(post.getPublishedAt().getTime());
                if (postDate.getTime() < startDate.getTime() || postDate.getTime() > endDate.getTime()) {
                    posts.remove(post);
                }
            }
        } catch (Exception e) {
            return posts;
        }
        return posts;
    }

    private Page<PostEntity> getPaginatedData(List<PostEntity> posts, int pageNo, int size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        long start = pageable.getOffset();
        long end =(start + pageable.getPageSize()) > posts.size() ? posts.size() : (start + pageable.getPageSize());
        Page<PostEntity> pages = new PageImpl<PostEntity>(posts.subList((int)start, (int)end), pageable, posts.size());
        return pages;
    }
}

