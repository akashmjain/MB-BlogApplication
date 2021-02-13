package com.akashmjain.BlogApplication.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
public class AdminRestController {
    @GetMapping("echo")
    public String echo() {
        return "echo from ADMIN";
    }
    
}
