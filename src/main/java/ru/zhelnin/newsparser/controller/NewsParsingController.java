package ru.zhelnin.newsparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zhelnin.newsparser.model.News;
import ru.zhelnin.newsparser.service.NewsFeedService;

@RestController
public class NewsParsingController {

    private NewsFeedService newsFeedService;

    @Autowired
    public NewsParsingController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @PostMapping(value = "/news/parse.do", produces = "application/json")
    public Iterable<News> parseNews() {
        return newsFeedService.updateNewsFeed();
    }
}
