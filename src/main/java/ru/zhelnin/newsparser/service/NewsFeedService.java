package ru.zhelnin.newsparser.service;

import ru.zhelnin.newsparser.dto.NewsFeedDto;
import ru.zhelnin.newsparser.model.News;
import ru.zhelnin.newsparser.model.NewsFeed;

import java.util.Collection;
import java.util.List;

public interface NewsFeedService {

    Collection<News> updateNewsFeed();

    List<NewsFeed> findExisting();

    NewsFeed createNewsFeed(NewsFeedDto newsFeedDto);

    void delete(long id);
}
