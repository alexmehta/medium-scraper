package com.trie.server.medium.requests.scraper;

import com.trie.server.medium.entities.Article;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController()
//@Mapping("/api/v1/articles")
public class ArticleRequestController {
    private final ArticleService articleService;

    public ArticleRequestController(ArticleService articleService) {
        this.articleService = articleService;
    }
    @PostMapping("/scrape")
    public Article scrape(String url) throws IOException, ParseException {

        return articleService.getArticle(url);
    }
}
