package com.dust.search.engine.ustaad.crawler.controller;

import com.dust.search.engine.ustaad.crawler.service.CrawlerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/crawler")
public class CrawlerController {
    private final CrawlerService crawlerService;

    public CrawlerController(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startCrawler(@RequestParam String seedUrl) {
        new Thread(() -> {
            try {
                crawlerService.startCrawling(seedUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }).start();
        return ResponseEntity.ok("Crawling started for seed URL: " + seedUrl);
    }
}

