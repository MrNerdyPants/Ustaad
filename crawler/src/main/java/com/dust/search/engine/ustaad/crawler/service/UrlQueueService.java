package com.dust.search.engine.ustaad.crawler.service;

import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class UrlQueueService {
    private final Queue<String> urlQueue = new ConcurrentLinkedQueue<>();
    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();

    public void addUrl(String url) {
        if (!visitedUrls.contains(url)) {
            urlQueue.add(url);
            visitedUrls.add(url);
        }
    }

    public String getNextUrl() {
        return urlQueue.poll();
    }
}

