package ru.zhelnin.newsparser.parser.rss;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.zhelnin.newsparser.model.News;
import ru.zhelnin.newsparser.model.NewsFeed;
import ru.zhelnin.newsparser.parser.rss.model.Channel;
import ru.zhelnin.newsparser.parser.rss.model.RssItem;
import ru.zhelnin.newsparser.service.NewsService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

public class RssParserTest {

    private static final int RSS = 2;

    private static final String FIRST_TITLE = "Армия Израиля сообщила о выпущенной со стороны Сектора Газа ракете";
    private static final String SECOND_TITLE = "Выпавший град образовал сугробы в Петербурге";
    private static final String FIRST_BODY = "С территории Сектора Газа в сторону Израиля была выпущена ракета. " +
            "Информация об этом появилось в Twitter ЦАХАЛ. Согласно сообщению ракета взорвалась в " +
            "воздухе, в результате инцидента никто не пострадал. Ракетную тревогу в связи с произошедшим " +
            "в стране не объявляли.";
    private static final String SECOND_BODY = "В некоторых районах Санкт-Петербурга прошел ливень и град. На опубликованных " +
            "пользователями фото и видео запечатлен покров из градин, по «сугробам» с трудом пробираются прохожие." +
            " «Сильный град в московском районе. Улицы устланы градинами размером с крупную черешню», — сообщается" +
            " в одной из групп.";
    private static final String FILE_PATH = "/rss_test.xml";

    private NewsFeed newsFeed;

    private RssItem firstItem;
    private RssItem secondItem;

    private News firstNews;
    private News secondNews;

    private Collection<RssItem> sourceItems;
    private Collection<News> finalNews;

    private NewsService newsService;

    @Before
    public void prepareObjects() throws MalformedURLException{
        newsFeed = new NewsFeed();
        newsFeed.setFeedType(RSS);
        newsFeed.setUrl("file://" + getClass().getResource(FILE_PATH).getPath());

        firstItem = new RssItem();
        firstItem.setTitle(FIRST_TITLE);
        firstItem.setDescription(FIRST_BODY);

        secondItem = new RssItem();
        secondItem.setTitle(SECOND_TITLE);
        secondItem.setDescription(SECOND_BODY);

        sourceItems = new ArrayList<>();
        sourceItems.add(firstItem);
        sourceItems.add(secondItem);

        firstNews = new News();
        firstNews.setTitle(FIRST_TITLE);
        firstNews.setBody(FIRST_BODY);
        firstNews.setNewsFeed(RSS);

        secondNews = new News();
        secondNews.setTitle(SECOND_TITLE);
        secondNews.setBody(SECOND_BODY);
        secondNews.setNewsFeed(RSS);

        finalNews = new ArrayList<>();
        finalNews.add(firstNews);
        finalNews.add(secondNews);
    }

    @Test
    public void fillNews() throws IOException {
        Assert.assertEquals(finalNews, new RssParser(newsFeed, newsService).fillNews());
    }

    @Test
    public void readChannel() {
        Channel channel = new Channel();
        channel.setTitle("Lenta.ru : Новости");
        channel.setDescription("Новости, статьи, фотографии, видео. Семь дней в неделю, 24 часа в сутки");
        channel.setItem(new ArrayList<>(sourceItems));
        channel.setLanguage("ru");

        Collection<RssItem> actualResult = new RssParser(newsFeed, newsService).readChannel(channel);
        Assert.assertEquals(sourceItems, actualResult);
    }

    @Test
    public void makeNews() {
        Collection<News> actualResult = new RssParser(newsFeed, newsService).makeNews(sourceItems);
        Assert.assertEquals(finalNews, actualResult);
    }
}
