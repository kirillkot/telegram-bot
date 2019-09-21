package ru.kirillkot.telegram.bot.client;

import feign.QueryMap;
import feign.RequestLine;
import ru.kirillkot.telegram.bot.client.dto.TopHeadlinesDto;

import java.util.Map;

public interface NewsClient {

    /**
     * <p>Request parameters
     * <p> country - The 2-letter ISO 3166-1 code of the country you want to get headlines for.
     * Possible options: ae ar at au be bg br ca ch cn co cu cz de eg fr gb gr hk hu id ie il in it jp kr lt lv ma mx my ng nl no nz ph pl pt ro rs ru sa se sg si sk th tr tw ua us ve za .
     * <p> Note: you can't mix this param with the sources param.
     *
     * <p> category - The category you want to get headlines for. Possible options: business entertainment general health science sports technology .
     * <p> Note: you can't mix this param with the sources param
     *
     * <p> sources - A comma-seperated string of identifiers for the news sources or blogs you want headlines from.
     * Use the /sources endpoint to locate these programmatically or look at the sources index.
     * <p> Note: you can't mix this param with the country or category params.
     *
     * <p> q - Keywords or a phrase to search for.
     *
     * <p> apiKey - Your API key. Alternatively you can provide this via the X-Api-Key HTTP header.
     */
    @RequestLine("GET /v2/top-headlines")
    TopHeadlinesDto topHeadlines(@QueryMap Map<String, Object> filter);
}
