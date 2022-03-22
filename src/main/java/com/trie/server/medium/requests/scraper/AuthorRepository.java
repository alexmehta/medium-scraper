package com.trie.server.medium.requests.scraper;

import com.trie.server.medium.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
