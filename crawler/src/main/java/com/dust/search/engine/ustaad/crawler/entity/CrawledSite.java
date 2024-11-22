package com.dust.search.engine.ustaad.crawler.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "crawled_site")
public class CrawledSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @Column(columnDefinition = "TEXT")
    private String content;

    public CrawledSite(String url, String content) {
        this.url = url;
        this.content = content;
    }
}
