package ru.zhelnin.newsparser.service;

import ru.zhelnin.newsparser.model.FeedType;

import java.util.List;

public interface FeedTypeService {

    List<FeedType> findAll();
}
