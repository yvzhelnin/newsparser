package ru.zhelnin.newsparser.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zhelnin.newsparser.model.News;
import ru.zhelnin.newsparser.service.NewsService;

@RestController
public class NewsGettingController {

    private NewsService newsService;

    public NewsGettingController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping(value = "/news/get.do", produces = "application/json")
    public Iterable<News> getNews() {
        return newsService.findAll();
    }
}
