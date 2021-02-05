package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.dao.UserRepository;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FilterController {
    @Autowired
    private UserService userService;

    @RequestMapping("/filter")
    public String getPostByAuthor(@RequestParam("userId") int userId, Model model) {
        Pageable page = PageRequest.of(0, 2);
        UserEntity userEntity = userService.findById(userId);
        List<PostEntity> postList = userEntity.getPosts();
        Page postPage = new PageImpl<PostEntity>(postList, page, postList.size());
        model.addAttribute("totalPages",postPage.getTotalPages());
        model.addAttribute("posts", postPage.getContent());
        return "index";
    }
}
