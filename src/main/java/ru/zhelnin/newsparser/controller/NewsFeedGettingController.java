package ru.zhelnin.newsparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.zhelnin.newsparser.model.NewsFeed;
import ru.zhelnin.newsparser.repository.NewsFeedRepository;
import ru.zhelnin.newsparser.service.NewsFeedService;

@RestController
public class NewsFeedGettingController {

    private NewsFeedService newsFeedService;

    @Autowired
    public NewsFeedGettingController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @PostMapping(value = "/news_feeds/get.do", produces = "application/json")
    public Iterable<NewsFeed> getFeedTypes() {
        return newsFeedService.findExisting();
    }
}
