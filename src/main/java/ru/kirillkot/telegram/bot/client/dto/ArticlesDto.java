package ru.kirillkot.telegram.bot.client.dto;

import lombok.Data;

@Data
public class ArticlesDto {

    private SourceDto source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;
}
