package ru.zhelnin.newsparser.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.zhelnin.newsparser.model.NewsFeed;
import ru.zhelnin.newsparser.parser.rss.RssParser;
import ru.zhelnin.newsparser.service.NewsService;

public class NewsParserFactoryTest {

    private static final int HTML = 1;
    private static final int RSS = 2;

    private NewsFeed newsFeed;
    private NewsService newsService;

    @Before
    public void fillNewsFeed() {
        newsFeed = new NewsFeed();
    }

    @Test
    public void getHtmlParser() {
        newsFeed.setFeedType(HTML);
        NewsParser parser = NewsParserFactory.getParser(newsFeed, newsService);

        Assert.assertTrue(parser instanceof HtmlParser);
    }

    @Test
    public void getRssParser() {
        newsFeed.setFeedType(RSS);
        NewsParser parser = NewsParserFactory.getParser(newsFeed, newsService);

        Assert.assertTrue(parser instanceof RssParser);
    }
}
