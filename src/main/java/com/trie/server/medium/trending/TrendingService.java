package com.trie.server.medium.trending;

import com.trie.server.medium.entities.Article;
import com.trie.server.medium.entities.Author;
import com.trie.server.medium.requests.scraper.article.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    public void getArticle(Document homepage, int n, List<Article> trending) throws IOException, ParseException, URISyntaxException {
        trending.add(articleService.getArticle(homepage.select("div.fv:nth-child(" + n + ") > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > a:nth-child(1)").attr("href")));
        System.out.println(Thread.currentThread().getName());
    }

    public List<Article> getTrendingArticles() throws IOException, ParseException, URISyntaxException {
        List<Article> trending = new ArrayList<>();
        Document homepage = Jsoup.connect("https://medium.com/").get();
        for (int i = 1; i <= 6; i++) {
            getArticle(homepage, i, trending);
        }
        return trending;
    }

    public List<String> getTrendingTags() throws IOException {
        List<String> tags = new ArrayList<>();
        Document jsoupDoc = Jsoup.connect("https://medium.com/").get();
        for (int i = 1; i <= 10; i++) {
            String s = jsoupDoc.toString();
            System.out.println(s);
        }
        return tags;
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
