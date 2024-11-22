package com.dust.search.engine.ustaad.crawler.service;

import com.dust.search.engine.ustaad.crawler.entity.CrawledSite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class CrawlerService {
    private final UrlQueueService urlQueueService;

    private final CrawledSiteService siteService;

    public CrawlerService(UrlQueueService urlQueueService, CrawledSiteService siteService) {
        this.urlQueueService = urlQueueService;
        this.siteService = siteService;
    }

    public void startCrawling(String seedUrl) {
        urlQueueService.addUrl(seedUrl);

        while (true) {
            String url = urlQueueService.getNextUrl();
            if (url == null) break; // Exit when the queue is empty

            try {
                // Fetch the content
                Document document = Jsoup.connect(url).get();
                System.out.println("Crawled URL: " + url);

                // Extract links
                Elements links = document.select("a[href]");
                for (Element link : links) {
                    String nextUrl = link.absUrl("href");
                    urlQueueService.addUrl(nextUrl);
                }

                // Optionally: Save the crawled content
                saveCrawledData(url, document.body().text());

            } catch (Exception e) {
                System.err.println("Failed to crawl URL: " + url);
            }
        }
    }

    private void saveCrawledData(String url, String content) {
        // Implement saving logic (e.g., to a database)
        System.out.println("Saving content for URL: " + url);
        siteService.save(new CrawledSite( url, content));
    }
}

