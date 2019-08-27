package ru.zhelnin.newsparser.repository;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zhelnin.newsparser.model.News;

import java.util.List;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {

    @NotFound(action = NotFoundAction.IGNORE)
    List<News> findAll();

    @Transactional
    News save(News news);

    @Query("select news from News news where lower(news.title) like lower(?1)")
    List<News> findByTitle(String titlePart);
}
