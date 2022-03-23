package com.trie.server.medium.requests.trending;

import com.trie.server.medium.entities.Article;
import com.trie.server.medium.entities.Author;
import com.trie.server.medium.requests.scraper.article.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrendingService {
    private final ArticleService articleService;

    public TrendingService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public List<Article> getTrendingArticles() throws IOException, ParseException, URISyntaxException {
        List<Article> trending = new ArrayList<>();
        Document homepage = Jsoup.connect("https://medium.com/").get();
        trending.add(articleService.getArticle(homepage.select("div.fv:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > a:nth-child(1)").attr("href")));
        trending.add(articleService.getArticle(homepage.select("div.fv:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > a:nth-child(1)").attr("href")));
        trending.add(articleService.getArticle(homepage.select("div.fv:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > a:nth-child(1)").attr("href")));
        trending.add(articleService.getArticle(homepage.select("div.fv:nth-child(4) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > a:nth-child(1)").attr("href")));
        trending.add(articleService.getArticle(homepage.select("div.fv:nth-child(5) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > a:nth-child(1)").attr("href")));
        trending.add(articleService.getArticle(homepage.select("div.fv:nth-child(6) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > a:nth-child(1)").attr("href")));
        return trending;
    }


    public List<Author> getTrendingAuthors() throws IOException, ParseException, URISyntaxException {
        List<Article> trendingArticles = getTrendingArticles();
        List<Author> trendingAuthors = new ArrayList<>();
        for (Article trendingArticle : trendingArticles) {
            trendingAuthors.add(trendingArticle.getAuthor());
        }
        return trendingAuthors;
    }
}
