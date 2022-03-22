package com.trie.server.medium.requests.scraper;

import com.trie.server.medium.entities.Article;
import com.trie.server.medium.entities.Author;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;

    public ArticleService(ArticleRepository articleRepository, AuthorRepository authorRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
    }

    public Article getArticle(String url) throws IOException, ParseException {
        List<Long> a = articleRepository.getIfExists(new URL(url));
        if (a != null&&a.size()!=0) return articleRepository.getById((a.get(0)));
        return articleRepository.save(scrapeArticle(url));
    }

    private Article scrapeArticle(String url) throws IOException, ParseException {
        Document doc = Jsoup.connect(url).get();
        Author author = getAuthor(doc);
        Article a = scrapeArticleContents(doc, author);
        author.getArticles().add(a);
        authorRepository.save(author);
        return articleRepository.save(a);
    }

    private Article scrapeArticleContents(Document doc, Author author) throws MalformedURLException, ParseException {
        String title =
                doc.select("div.hr:nth-child(2) > div:nth-child(1)").get(0).text();
        String url = doc.location();
        return new Article(title, author, doc.select(".pw-published-date").text(), new URL(url));
    }

    private Author getAuthor(Document doc) throws IOException {
        Element element = doc.select(".gj > div:nth-child(1) > div:nth-child(1)").get(0);
        Document author = Jsoup.connect(doc.location() + element.attr("href")).get();
        return new Author(author.location().split("\\.")[0], element.text());
    }
}
