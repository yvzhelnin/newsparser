package ru.zhelnin.newsparser.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Component
@NoArgsConstructor
@Data
@Entity
@Table(name = "t_news",
        indexes = @Index(name = "title_index", unique = true, columnList = "title")
)
public class News implements Serializable {

    private static final long serialVersionUID = -7049957706738879274L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private NewsFeed newsFeed;
}
