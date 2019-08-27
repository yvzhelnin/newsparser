package ru.zhelnin.newsparser.parser.rss.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class RssItem {

    private String guid;
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private String category;
}
