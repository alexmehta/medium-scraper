package com.trie.server.medium.requests.scraper.article;

import com.trie.server.medium.entities.Article;
import com.trie.server.medium.entities.Author;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
        if (a != null && a.size() == 1) {
            return articleRepository.getById((a.get(0)));
        }
        return articleRepository.save(scrapeArticle(url));
    }

    private Article scrapeArticle(String url) throws IOException, ParseException {
        long time = System.currentTimeMillis();
        Document doc = Jsoup.connect(url).get();
        System.out.println("connected " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        Author author = (authorRepository.getIfExists(getAuthor(doc).getUsername()).size() == 1) ?
                (authorRepository.getById((authorRepository.getIfExists(getAuthor(doc).getUsername()).get(0)))) : getAuthor(doc);
        System.out.println("got author " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        Article a = scrapeArticleContents(doc, author);
        System.out.println("got article " + (System.currentTimeMillis() - time));
        author.getArticles().add(a);
        authorRepository.save(author);
        System.out.println("done");
        return articleRepository.save(a);
    }

    private Article scrapeArticleContents(Document doc, Author author) throws MalformedURLException, ParseException {
        String title = doc.getElementsByClass("pw-post-title").get(0).text();
        String url = doc.location();
        String topic = doc.select("body > script:nth-child(4)").text();
        return new Article(title, author, doc.select(".pw-published-date").text(), new URL(url));
    }

    private Author getAuthor(Document doc) throws IOException {
        Elements element = doc.select(".pw-author-name");
        Elements elements = doc.select(".gj > div:nth-child(1) > div:nth-child(1)");
        if (element.size() == 0) element = elements;
        Document author = Jsoup.connect(doc.location() + element.get(0).attr("href")).get();
        return new Author(author.location().split("\\.")[0].substring(8), element.text());
    }
}
