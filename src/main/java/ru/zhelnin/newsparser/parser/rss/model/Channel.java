package ru.zhelnin.newsparser.parser.rss.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Channel {

    private String title;
    private String link;
    private String description;
    private String language;
    private String lastBuildDate;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<RssItem> item;
}
