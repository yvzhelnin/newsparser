package ru.zhelnin.newsparser.parser;

import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Test;
import ru.zhelnin.newsparser.model.News;
import ru.zhelnin.newsparser.util.StringToInputStreamConverter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public class HtmlParserTest {

    @Test
    public void getNewsUrls() throws IOException {
        Collection<String> expectedResult = new HashSet<>();
        expectedResult.add("/news/spool/news_id-472919-section_id-252.html?from=storyline");
        expectedResult.add("/news/spool/news_id-471763-section_id-160.html?from=storyline");
        expectedResult.add("/news/spool/news_id-471562.html?from=storyline");
        String html = StringToInputStreamConverter.convert(getClass().getResourceAsStream("/new_urls_test.html"));
        Collection<String> actualResult = new HtmlParser().getNewsUrls(Jsoup.parse(html), "/news/spool/news_id-");

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void parseBody() throws IOException {
        News expectedResult = new News();
        expectedResult.setTitle("Обещали две недели: в Екатеринбурге закрыли движение по Фурманова");
        expectedResult.setBody("На улице начали ремонтировать трубопровод.В Екатеринбурге закрыли движение по Фурманова. " +
                "Улицу планировали перекрыть с 11 июля, потом перенесли срок на 17 июля, но сделали это только сегодня.– " +
                "Вместо 17 июля движение перекрыли с сегодняшнего дня. В сторону Щорса по Московской вроде открыто движение. " +
                "76-й автобус с центра едет по Московской со стороны \"Фан-Фана\", – рассказала Е1.RU читательница Валентина." +
                "Напомним, на Фурманова до начала октября будут ремонтировать магистральные сети.\"На объекте предстоит " +
                "заменить трубопровод диаметром 500 миллиметров протяжённостью 730 метров. Новые трубы будут изолированы " +
                "прошивными матами – гибкими теплоизоляционными изделиями с облицовкой стальной сеткой, механически соединённой " +
                "с теплоизоляционным материалом, – и покрыты алюминиевой армированной фольгой. Предусмотрена установка сильфонных" +
                " компенсаторов, более современных и надёжных по сравнению с сальниковыми. Применение современных материалов " +
                "и технологий продлит срок службы трубопровода до 30 лет\", – ранее сообщили в ЕТК.Текст: Ирина АХМЕТШИНА" +
                " Фото: читательница Е1. RU Валентина");
        String html = StringToInputStreamConverter.convert(getClass().getResourceAsStream("/news_body_parse_test.html"));

        News actualResult = new HtmlParser().parseBody(Jsoup.parse(html), "td", "news");
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void parseBodyWithNotExistingBodyWrapperClass() throws IOException {
        News expectedResult = new News();
        expectedResult.setTitle("Обещали две недели: в Екатеринбурге закрыли движение по Фурманова");
        expectedResult.setBody("");
        String html = StringToInputStreamConverter.convert(getClass().getResourceAsStream("/news_body_parse_test.html"));

        News actualResult = new HtmlParser().parseBody(Jsoup.parse(html), "td", "ewewq");
        Assert.assertEquals(expectedResult, actualResult);
    }
}
