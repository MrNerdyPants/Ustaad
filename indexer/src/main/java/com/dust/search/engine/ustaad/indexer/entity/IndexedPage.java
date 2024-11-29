package com.dust.search.engine.ustaad.indexer.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Data
@Entity
@Table(name = "indexed_pages")
public class IndexedPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String url;

    private String title;

    @Column(name = "meta_description", columnDefinition = "TEXT")
    private String metaDescription;

    @ElementCollection
    @CollectionTable(name = "tokens", joinColumns = @JoinColumn(name = "indexed_page_id"))
    @Column(name = "token", columnDefinition = "TEXT")
    private Map<String, Integer> tokens; // Token and its frequency

    @Column(name = "language")
    private String language;

    @Column(name = "processed_timestamp")
    private Long processedTimestamp;

}
