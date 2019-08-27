package ru.zhelnin.newsparser.parser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.zhelnin.newsparser.model.News;
import ru.zhelnin.newsparser.model.NewsFeed;
import ru.zhelnin.newsparser.service.NewsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class HtmlParser implements NewsParser {

    private static final String LINK_TAG = "a";
    private static final String URL_ATTRIBUTE = "href";

    private NewsFeed newsFeed;
    private NewsService newsService;

    public void parse() throws IOException {
        Iterable<String> newsUrls = getNewsUrls(Jsoup.connect(newsFeed.getUrl()).get(), newsFeed.getNewsPrefix());
        collectNewsFromFeed(newsFeed, newsUrls);
    }

    private void collectNewsFromFeed(NewsFeed feed, Iterable<String> newsUrls) {
        Collection<News> parsed = new ArrayList<>();
        for (String url : newsUrls) {
            Document document = null;
            try {
                document = Jsoup.connect(feed.getDomain() + url).get();
            } catch (IOException e) {
                // Add logging
            }
            News news = parseBody(document, feed.getBodyTag(), feed.getBodyWrapperClass());
            news.setNewsFeed(feed);
            parsed.add(news);
        }
        newsService.saveOrUpdate(parsed);
    }

    Collection<String> getNewsUrls(Document document, String prefix) {
        Elements elements = document.select(LINK_TAG);
        if (elements == null || elements.isEmpty()) {
            return Collections.emptyList();
        }
        return elements.stream()
                .map(currentElement -> currentElement.attr(URL_ATTRIBUTE))
                .filter(currentHref -> currentHref.startsWith(prefix))
                .collect(Collectors.toSet());
    }

    News parseBody(Document document, String bodyTag, String bodyClass) {
        News news = new News();
        news.setTitle(document.title());
        news.setBody(buildNewsBody(findBodyWrapper(document.getElementsByTag(bodyTag), bodyClass)));

        return news;
    }

    private String buildNewsBody(Element bodyWrapper) {
        if (bodyWrapper == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Element paragraph : bodyWrapper.getElementsByTag("p")) {
            builder.append(paragraph.text());
        }
        return builder.toString();
    }

    private Element findBodyWrapper(Elements bodyCandidates, String bodyClass) {
        if (bodyCandidates == null || bodyCandidates.isEmpty()) {
            return null;
        }
        return bodyCandidates.stream()
                .filter(candidate -> candidate.hasClass(bodyClass))
                .findAny()
                .orElse(null);
    }
}
