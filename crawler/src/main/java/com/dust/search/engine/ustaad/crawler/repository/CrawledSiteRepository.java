package com.dust.search.engine.ustaad.crawler.repository;

import com.dust.search.engine.ustaad.crawler.entity.CrawledSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrawledSiteRepository extends JpaRepository<CrawledSite, Long> {
    Optional<CrawledSite> getByUrl(String url);
}
