package ru.zhelnin.newsparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.zhelnin.newsparser.dto.NewsFeedDto;
import ru.zhelnin.newsparser.service.NewsFeedService;

@RestController
public class NewsFeedCreationController {

    private NewsFeedService newsFeedService;

    @Autowired
    public NewsFeedCreationController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @PostMapping(value = "/newsfeed/add.do", produces = "text/plain; charset=utf-8")
    public String createNewsFeed(@RequestBody NewsFeedDto newsFeedDto) {
        newsFeedService.createNewsFeed(newsFeedDto);

        return "Лента новостей успешно добавлена";
    }
}
