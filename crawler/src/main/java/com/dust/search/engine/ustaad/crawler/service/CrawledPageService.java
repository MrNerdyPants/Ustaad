package com.dust.search.engine.ustaad.crawler.service;

import com.dust.search.engine.ustaad.crawler.entity.CrawledPage;
import com.dust.search.engine.ustaad.crawler.repository.CrawledPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrawledPageService {
    @Autowired
    CrawledPageRepository CrawledPageRepository;

    CrawledPage save(CrawledPage CrawledPage) {
        return CrawledPageRepository.save(CrawledPage);
    }

    CrawledPage getByUrl(String url) {
        return CrawledPageRepository.getByUrl(url).orElseThrow(() -> new RuntimeException("Record not Exist!"));
    }

    Boolean existByUrl(String url) {
        return CrawledPageRepository.existsByUrl(url);
    }
}
