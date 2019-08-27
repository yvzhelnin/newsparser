package ru.zhelnin.newsparser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavigationController {

    @GetMapping(value = "/create_feed.do")
    public ModelAndView goToNewsFeeds() {
        return new ModelAndView("create_feed");
    }
}
