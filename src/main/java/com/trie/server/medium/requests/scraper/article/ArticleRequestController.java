package com.trie.server.medium.requests.scraper.article;

import com.trie.server.medium.entities.Article;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

@RestController()
public class ArticleRequestController {
    private final ArticleService articleService;

    public ArticleRequestController(ArticleService articleService) {
        this.articleService = articleService;
    }
    @PostMapping("/api/v1/scrape")
    public Article scrape(String url) throws IOException, ParseException, URISyntaxException {

        return articleService.getArticle(url);
    }
}
