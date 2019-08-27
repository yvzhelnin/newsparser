package ru.zhelnin.newsparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.zhelnin.newsparser.model.FeedType;
import ru.zhelnin.newsparser.service.FeedTypeService;

@RestController
public class FeedTypesGettingController {

    private FeedTypeService feedTypeService;

    @Autowired
    public FeedTypesGettingController(FeedTypeService feedTypeService) {
        this.feedTypeService = feedTypeService;
    }

    @PostMapping(value = "/feed_types/get.do", produces = "application/json")
    public Iterable<FeedType> getFeedTypes() {
        return feedTypeService.findAll();
    }
}
