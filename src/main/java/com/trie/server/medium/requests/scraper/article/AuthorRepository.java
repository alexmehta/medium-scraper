package com.trie.server.medium.requests.scraper.article;

import com.trie.server.medium.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.net.URL;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT id FROM Author where username = :username")
    List<Long> getIfExists(String username);
}
