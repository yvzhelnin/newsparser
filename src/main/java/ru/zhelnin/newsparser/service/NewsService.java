package ru.zhelnin.newsparser.service;

import org.springframework.transaction.annotation.Transactional;
import ru.zhelnin.newsparser.model.News;

import java.util.Collection;
import java.util.List;

public interface NewsService {

    List<News> findAll();

    List<News> findByTitle(String titlePart);

    void saveOrUpdate(Collection<News> news);
}
