package ru.zhelnin.newsparser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhelnin.newsparser.dto.NewsFeedDto;
import ru.zhelnin.newsparser.model.News;
import ru.zhelnin.newsparser.model.NewsFeed;
import ru.zhelnin.newsparser.model.SourceType;
import ru.zhelnin.newsparser.parser.NewsParserFactory;
import ru.zhelnin.newsparser.repository.FeedTypeRepository;
import ru.zhelnin.newsparser.repository.NewsFeedRepository;
import ru.zhelnin.newsparser.service.NewsFeedService;
import ru.zhelnin.newsparser.service.NewsService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Service
public class NewsFeedServiceImpl implements NewsFeedService {

    private NewsService newsService;
    private NewsFeedRepository newsFeedRepository;
    private FeedTypeRepository feedTypeRepository;

    @Autowired
    public NewsFeedServiceImpl(NewsService newsService,
                               NewsFeedRepository newsFeedRepository,
                               FeedTypeRepository feedTypeRepository) {
        this.newsService = newsService;
        this.newsFeedRepository = newsFeedRepository;
        this.feedTypeRepository = feedTypeRepository;
    }

    public Collection<News> updateNewsFeed() {
        Collection<NewsFeed> feeds = newsFeedRepository.findExisting();
        for (NewsFeed feed : feeds) {
            try {
                NewsParserFactory.getParser(feed, newsService).parse();
            } catch (IOException e) {
                //TODO: add logging
            }
        }
        return newsService.findAll();
    }

    @Override
    public List<NewsFeed> findExisting() {
        return newsFeedRepository.findExisting();
    }

    @Override
    public NewsFeed createNewsFeed(NewsFeedDto dto) {
        NewsFeed newsFeed = new NewsFeed();
        newsFeed.setUrl(dto.getFeedUrl());
        newsFeed.setFeedType(feedTypeRepository.findById(dto.getSourceType()));
        newsFeed.setDomain(dto.getDomain());
        newsFeed.setNewsPrefix(dto.getPrefix());
        newsFeed.setBodyTag(dto.getBodyTag());
        newsFeed.setBodyWrapperClass(dto.getBodyWrapperClass());
        newsFeed.setDeleted(false);
        newsFeedRepository.save(newsFeed);

        return newsFeed;
    }

    @Override
    public void delete(long id) {
        newsFeedRepository.setDeleted(id);
    }
}
