package ru.zhelnin.newsparser.parser.rss;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import ru.zhelnin.newsparser.model.News;
import ru.zhelnin.newsparser.model.NewsFeed;
import ru.zhelnin.newsparser.parser.NewsParser;
import ru.zhelnin.newsparser.parser.rss.model.Channel;
import ru.zhelnin.newsparser.parser.rss.model.Rss;
import ru.zhelnin.newsparser.parser.rss.model.RssItem;
import ru.zhelnin.newsparser.service.NewsService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class RssParser implements NewsParser {

    private NewsFeed newsFeed;
    private NewsService newsService;

    public void parse() throws IOException {
        for (News current : fillNews()) {
            newsService.saveOrUpdate(current);
        }
    }

    Iterable<News> fillNews() throws IOException {
        return makeNews(readChannel(new XmlMapper().readValue(new URL(newsFeed.getUrl()), Rss.class).getChannel()));
    }

    Collection<RssItem> readChannel(Channel channel) {
        Iterable<RssItem> items = channel.getItem();
        if (items != null) {
            return channel.getItem();
        }
        return Collections.emptyList();
    }

    Collection<News> makeNews(Collection<RssItem> items) {
        Collection<News> result = new ArrayList<>();
        for (RssItem item : items) {
            News current = new News();
            current.setNewsFeed(newsFeed);
            current.setTitle(item.getTitle().trim());
            current.setBody(item.getDescription().trim());
            result.add(current);
        }
        return result;
    }
}
