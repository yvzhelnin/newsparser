package ru.zhelnin.newsparser.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "t_newsfeed")
public class NewsFeed implements Serializable {

    private static final long serialVersionUID = -7049957706738879274L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private FeedType feedType;

    @Column(name = "domain")
    private String domain;

    @Column(name = "news_prefix")
    private String newsPrefix;

    @Column(name = "body_tag")
    private String bodyTag;

    @Column(name = "body_wrapper_class")
    private String bodyWrapperClass;

    @Column(name = "deleted")
    private boolean deleted;
}
