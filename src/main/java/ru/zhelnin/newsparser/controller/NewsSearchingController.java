package ru.zhelnin.newsparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.zhelnin.newsparser.model.News;
import ru.zhelnin.newsparser.repository.NewsRepository;
import ru.zhelnin.newsparser.service.NewsService;

@RestController
public class NewsSearchingController {

    private NewsService newsService;

    @Autowired
    public NewsSearchingController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping(value = "/news/search.do", produces = "application/json")
    public Iterable<News> searchNews(@RequestParam(value = "titlePart") String titlePart) {
        return newsService.findByTitle(titlePart);
    }
}
