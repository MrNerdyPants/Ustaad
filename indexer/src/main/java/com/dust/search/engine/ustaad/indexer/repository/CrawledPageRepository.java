package com.dust.search.engine.ustaad.indexer.repository;


import com.dust.search.engine.ustaad.indexer.entity.CrawledPage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrawledPageRepository extends JpaRepository<CrawledPage, Long> {
    Optional<CrawledPage> getByUrl(String url);

    boolean existsByUrl(String url);

    List<CrawledPage> findAllByIdGreaterThan(Long id, Pageable pageable);
}
