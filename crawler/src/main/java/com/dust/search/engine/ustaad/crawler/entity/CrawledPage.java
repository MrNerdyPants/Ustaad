
package com.dust.search.engine.ustaad.crawler.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "crawled_page")
public class CrawledPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "meta_description", columnDefinition = "TEXT")
    private String metaDescription;

    @Column(name = "keywords", columnDefinition = "TEXT")
    private String keywords;

    @Column(name = "main_content", columnDefinition = "TEXT")
    private String mainContent;

    @ElementCollection
    @CollectionTable(name = "crawled_headers", joinColumns = @JoinColumn(name = "page_id"))
    @Column(name = "header", columnDefinition = "TEXT")
    private List<String> headers;

    @ElementCollection
    @CollectionTable(name = "crawled_outbound_links", joinColumns = @JoinColumn(name = "page_id"))
    @Column(name = "link", columnDefinition = "TEXT")
    private List<String> outboundLinks;


    @Column(name = "http_status_code")
    private Integer httpStatusCode;

    @Column(name = "crawl_timestamp", nullable = false)
    private LocalDateTime crawlTimestamp;

    @Column(name = "canonical_url")
    private String canonicalUrl;

    private String language;

    @PrePersist
    private void onCreate() {
        this.crawlTimestamp = LocalDateTime.now();
    }

}
