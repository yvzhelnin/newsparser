package ru.zhelnin.newsparser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "t_dic_feed_type")
public class FeedType implements Serializable {

    private static final long serialVersionUID = -5170875020617735653L;

    @Id
    @Enumerated(value = EnumType.ORDINAL)
    private SourceType id;

    @Column(name = "description")
    private String description;

    @Column(name = "arguments_amount")
    private int argumentsAmount;
}
