package ru.zhelnin.newsparser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zhelnin.newsparser.model.SourceType;

@Data
@NoArgsConstructor
public class NewsFeedDto {

    private String feedUrl;
    private SourceType sourceType;
    private String domain;
    private String prefix;
    private String bodyTag;
    private String bodyWrapperClass;
}
