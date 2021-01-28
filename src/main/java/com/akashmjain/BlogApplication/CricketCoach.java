package com.akashmjain.BlogApplication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class CricketCoach implements Coach{


    @Override
    @RequestMapping("/")
    public String getDailySchedule() {
        return "Hello World";
    }

}
