package com.trie.server.medium.requests.trending;

import com.trie.server.medium.entities.Article;
import com.trie.server.medium.entities.Author;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@RestController
public class TrendingController {
    private final TrendingService trendingService;

    public TrendingController(TrendingService trendingService) {
        this.trendingService = trendingService;
    }


    @GetMapping("/api/v1/trending/articles")
    public List<Article> getTrendingArticles() throws IOException, ParseException, URISyntaxException {
        return trendingService.getTrendingArticles();
    }

    @GetMapping("/api/v1/trending/authors")
    public List<Author> getTrendingAuthors() throws IOException, ParseException, URISyntaxException {
        return trendingService.getTrendingAuthors();
    }

}
