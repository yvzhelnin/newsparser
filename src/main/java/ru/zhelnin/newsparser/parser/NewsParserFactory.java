package ru.zhelnin.newsparser.parser;

import ru.zhelnin.newsparser.model.NewsFeed;
import ru.zhelnin.newsparser.model.SourceType;
import ru.zhelnin.newsparser.parser.rss.RssParser;
import ru.zhelnin.newsparser.service.NewsService;

public class NewsParserFactory {

    public static NewsParser getParser(NewsFeed newsFeed, NewsService newsService) {
        return newsFeed.getFeedType().getId() == SourceType.HTML ?
                new HtmlParser(newsFeed, newsService) :
                new RssParser(newsFeed, newsService);
    }
}
