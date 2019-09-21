package ru.kirillkot.telegram.bot.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class TopHeadlinesDto {
    private String ok;
    private Integer totalResults;
    private List<ArticlesDto> articles;
}
