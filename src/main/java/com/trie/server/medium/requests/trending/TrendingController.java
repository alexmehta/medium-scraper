package com.trie.server.medium.requests.trending;

import com.trie.server.medium.entities.Article;
import com.trie.server.medium.entities.Author;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
public class TrendingController {
    private final TrendingService trendingService;

    public TrendingController(TrendingService trendingService) {
        this.trendingService = trendingService;
    }


    @PostMapping("/api/v1/trending/articles")
    public List<Article> getTrendingArticles() throws IOException, ParseException {
        return trendingService.getTrendingArticles();
    }
    @PostMapping("/api/v1/trending/authors")
    public List<Author> getTrendingAuthors() throws IOException, ParseException {
        return trendingService.getTrendingAuthors();
    }

}
