package com.trie.server.medium.entities;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

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
    @Column(nullable = false, length = 512, unique = true)
    @NotNull(message = "URL cannot be null")

    private URL url;

    public Article() {
    }

    public Article(String title, Author author, String strpublishDate, URL url) {
        this.title = title;
        this.author = author;
        try {
            this.publishDate = LocalDate.parse(strpublishDate, formatterWithYear);
        } catch (DateTimeParseException parseException) {
            this.publishDate = LocalDate.parse(strpublishDate + ", 2022", formatterWithYear);
        }
        this.url = url;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publishDate=" + publishDate +
                ", url=" + url +
                '}';
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


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
