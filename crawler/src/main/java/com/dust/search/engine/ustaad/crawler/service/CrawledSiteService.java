package com.dust.search.engine.ustaad.crawler.service;

import com.dust.search.engine.ustaad.crawler.entity.CrawledSite;
import com.dust.search.engine.ustaad.crawler.repository.CrawledSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrawledSiteService {
    @Autowired
    CrawledSiteRepository crawledSiteRepository;

    CrawledSite save(CrawledSite crawledSite){
        return crawledSiteRepository.save(crawledSite);
    }

    CrawledSite getByUrl(String url){
        return crawledSiteRepository.getByUrl(url).orElseThrow(()-> new RuntimeException("Record not Exist!"));
    }
}
