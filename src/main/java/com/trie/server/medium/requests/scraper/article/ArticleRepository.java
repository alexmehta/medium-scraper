package com.trie.server.medium.requests.scraper.article;

import com.trie.server.medium.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.net.URL;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("FROM Article a where a.url = :url")
    List<Article> getIfExists(URL url);
}
