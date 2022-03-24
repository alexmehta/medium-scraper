package com.trie.server.medium.requests.scraper.article;

import com.trie.server.medium.entities.Article;
import com.trie.server.medium.entities.Author;
import net.bytebuddy.asm.Advice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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

    public URL sanatizeURL(String url) throws IOException, URISyntaxException {
        URI u = new URI(url);
        return new URI(u.getScheme(), u.getAuthority(), u.getPath(), null, u.getFragment()).toURL();
    }
    public Article getArticle(String url) throws IOException, ParseException, URISyntaxException {
        URL sanitizeURL = sanatizeURL(url);
        long system = System.currentTimeMillis();
        List<Article> a = articleRepository.getIfExists(sanitizeURL);
        if (a != null && a.size() == 1) {
            return a.get(0);
        }
        return articleRepository.save(scrapeArticle(Jsoup.connect(url).get().location()));
    }

    private Article scrapeArticle(String url) throws IOException, ParseException, URISyntaxException {
        Document doc = Jsoup.connect(url).get();
        Author author = (authorRepository.getIfExists(getAuthor(doc).getUsername()).size() == 1) ?
                (authorRepository.getById((authorRepository.getIfExists(getAuthor(doc).getUsername()).get(0)))) : getAuthor(doc);
        Article a = scrapeArticleContents(doc, author);
        author.getArticles().add(a);
        authorRepository.save(author);
        return articleRepository.save(a);
    }

    private Article scrapeArticleContents(Document doc, Author author) throws IOException, URISyntaxException {
        String title = doc.getElementsByClass("pw-post-title").get(0).text();
        String url = doc.location();
        Elements element = doc.select(".pw-author-name");
        Elements elements = doc.select(".gj > div:nth-child(1) > div:nth-child(1)");
        if (element.size() == 0) element = elements;
        Document authorDoc = Jsoup.connect(doc.location() + element.get(0).attr("href")).get();
        Elements elementgroup = authorDoc.select("div:contains(" + title + ")");
        System.out.println(elementgroup);
        return new Article(title, author, doc.select(".pw-published-date > span:nth-child(1)").text(), sanatizeURL(url));
    }

    private Author getAuthor(Document doc) throws IOException {
        Elements element = doc.select(".pw-author-name");
        Elements elements = doc.select(".gj > div:nth-child(1) > div:nth-child(1)");
        if (element.size() == 0) element = elements;
        Document author = Jsoup.connect(doc.location() + element.get(0).attr("href")).get();
        return new Author(author.location().split("\\.")[0].substring(8), element.text());
    }
}
