package ru.zhelnin.newsparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zhelnin.newsparser.service.NewsFeedService;

@RestController
public class NewsFeedDeletionController {

    private NewsFeedService newsFeedService;

    @Autowired
    public NewsFeedDeletionController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @PostMapping(value = "/newsfeed/delete.do", produces = "text/plain; charset=utf-8")
    public String createNewsFeed(@RequestParam(value = "id") long id) {
        newsFeedService.delete(id);

        return "Лента новостей удалена";
    }
}
