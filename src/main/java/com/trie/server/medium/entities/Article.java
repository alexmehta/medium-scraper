package com.trie.server.medium.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@Entity
public class Article {
    @Transient
    private static final DateTimeFormatter formatterWithYear = DateTimeFormatter.ofPattern("MMM d, y");
    @Transient
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d");
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;
    @Column(nullable = false)
    private String title;
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private Author author;
    @Column
    private LocalDate publishDate;
    @Column(nullable = false, unique = true, length = 512)
    @NotNull(message = "URL cannot be null")
    private URL url;
    public Article() {
    }

    public Article(String title, Author author, String publishDate, URL url) {
        this.title = title;
        this.author = author;
        try {
            this.publishDate = LocalDate.parse(publishDate, formatterWithYear);
        } catch (DateTimeParseException parseException) {
            this.publishDate = LocalDate.parse(publishDate + ", 2022", formatterWithYear);
        }
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }


    @Transient
    public Integer getDaysOld() {
        return Math.toIntExact(ChronoUnit.DAYS.between(publishDate, LocalDate.now()));
    }


}
