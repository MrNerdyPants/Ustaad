package com.dust.search.engine.ustaad.indexer.repository;


import com.dust.search.engine.ustaad.indexer.entity.IndexedPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexedPageRepository extends JpaRepository<IndexedPage, Long> {
    boolean existsByUrl(String url);
}

