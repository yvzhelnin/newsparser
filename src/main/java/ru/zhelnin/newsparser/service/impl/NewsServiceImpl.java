package ru.zhelnin.newsparser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhelnin.newsparser.model.News;
import ru.zhelnin.newsparser.repository.NewsRepository;
import ru.zhelnin.newsparser.service.NewsService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private NewsRepository newsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public NewsServiceImpl(@Qualifier("newsRepository") NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public List<News> findByTitle(String titlePart) {
        return newsRepository.findByTitle(titlePart);
    }

    @Transactional
    public void saveOrUpdate(Collection<News> newsForUpdate) {
        for (News news : newsForUpdate) {
            javax.persistence.Query searchQuery = entityManager.createQuery("select count(*) from News n where n.title = :news_title", Long.class);
            searchQuery.setParameter("news_title", news.getTitle());
            if (searchQuery.getSingleResult().equals(0L)) {
                entityManager.persist(news);
                entityManager.refresh(news);
            }
        }
    }
}
