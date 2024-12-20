package com.dust.search.engine.ustaad.crawler.repository;


import com.dust.search.engine.ustaad.crawler.entity.CrawledPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrawledPageRepository extends JpaRepository<CrawledPage, Long> {
    Optional<CrawledPage> getByUrl(String url);

    boolean existsByUrl(String url);
}
