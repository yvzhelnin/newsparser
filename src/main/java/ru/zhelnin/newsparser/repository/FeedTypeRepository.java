package ru.zhelnin.newsparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zhelnin.newsparser.model.FeedType;
import ru.zhelnin.newsparser.model.SourceType;

import java.util.List;

@Repository
public interface FeedTypeRepository extends JpaRepository<FeedType, Long> {

    List<FeedType> findAll();

    FeedType findById(SourceType id);
}
