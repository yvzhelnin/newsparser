package ru.zhelnin.newsparser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.zhelnin.newsparser.model.FeedType;
import ru.zhelnin.newsparser.repository.FeedTypeRepository;
import ru.zhelnin.newsparser.service.FeedTypeService;

import java.util.List;

@Service
public class FeedTypeServiceImpl implements FeedTypeService {

    private FeedTypeRepository feedTypeRepository;

    @Autowired
    public FeedTypeServiceImpl(@Qualifier(value = "feedTypeRepository") FeedTypeRepository feedTypeRepository) {
        this.feedTypeRepository = feedTypeRepository;
    }

    @Cacheable(value = "feedTypes")
    public List<FeedType> findAll() {
        return feedTypeRepository.findAll();
    }
}
