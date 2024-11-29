package com.dust.search.engine.ustaad.indexer.service;

import com.dust.search.engine.ustaad.indexer.entity.CrawledPage;
import com.dust.search.engine.ustaad.indexer.repository.CrawledPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrawledPageService {

    private Long consumedRecordId = 0L;

    private int pageSize = 1000;

    @Autowired
    CrawledPageRepository crawledPageRepository;

    public List<CrawledPage> consume() {
        List<CrawledPage> crawledPages = crawledPageRepository.findAllByIdGreaterThan(consumedRecordId, Pageable.ofSize(pageSize));
        consumedRecordId += pageSize;
        return crawledPages;
    }

    public CrawledPage save(CrawledPage CrawledPage) {
        return crawledPageRepository.save(CrawledPage);
    }

    public CrawledPage getByUrl(String url) {
        return crawledPageRepository.getByUrl(url).orElseThrow(() -> new RuntimeException("Record not Exist!"));
    }

    public Boolean existByUrl(String url) {
        return crawledPageRepository.existsByUrl(url);
    }
}
