package com.trie.server.tasks;

import com.trie.server.medium.trending.TrendingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

@Component
public class CacheDaily {
    private final TrendingService trendingService;

    public CacheDaily(TrendingService trendingService) {
        this.trendingService = trendingService;
    }

    @Scheduled(fixedDelay = 50000)
    public void getDaily() throws IOException, ParseException, URISyntaxException {
        System.out.println("running cron job to get trending page");
        trendingService.getTrendingArticles();
    }
}
