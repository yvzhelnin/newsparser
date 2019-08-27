package ru.zhelnin.newsparser.repository;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zhelnin.newsparser.model.NewsFeed;

import java.util.List;

@Repository
public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {

    @NotFound(action = NotFoundAction.IGNORE)
    @Query("select newsFeed from NewsFeed newsFeed where newsFeed.deleted = false")
    List<NewsFeed> findExisting();

    NewsFeed findOne(Long id);

    @Transactional
    NewsFeed save(NewsFeed newsFeed);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update NewsFeed newsFeed set newsFeed.deleted = true where newsFeed.id = ?1")
    void setDeleted(long id);
}
