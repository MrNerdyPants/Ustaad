package com.dust.search.engine.ustaad.crawler.service;

import com.dust.search.engine.ustaad.crawler.entity.CrawledPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static com.dust.search.engine.ustaad.crawler.service.RobotParser.robotSafe;

@Service
public class CrawlerService {
    private final UrlQueueService urlQueueService;

    private final CrawledPageService siteService;

    public CrawlerService(UrlQueueService urlQueueService, CrawledPageService siteService) {
        this.urlQueueService = urlQueueService;
        this.siteService = siteService;
    }

    public void startCrawling(String seedUrl) throws MalformedURLException {
        urlQueueService.addUrl(seedUrl);

        while (true) {
            String url = urlQueueService.getNextUrl();
            if (url == null) break; // Exit when the queue is empty

            if (siteService.existByUrl(url)) continue;

            try {
                URL site = new URL(url);
                if (!robotSafe(new URL(url))) {
                    continue;
                }
            } catch (Exception e) {
                continue;
            }

            try {
                // Fetch the content
                Connection connection = Jsoup.connect(url);
                Document document = connection.get();
                System.out.println("Crawled URL: " + url);

                // Extract links
                Elements links = document.select("a[href]");
                for (Element link : links) {
                    String nextUrl = link.absUrl("href");
                    urlQueueService.addUrl(nextUrl);
                }


                // Optionally: Save the crawled content
                saveCrawledData(url, document);

            } catch (Exception e) {
                System.err.println("Failed to crawl URL: " + url);
            }
        }
    }

    private void saveCrawledData(String url, Document document) {
        // Implement saving logic (e.g., to a database)
        System.out.println("Saving content for URL: " + url);

        // Extract data
        String title = document.title();
        String metaDescription = document.select("meta[name=description]").attr("content");
        String keywords = document.select("meta[name=keywords]").attr("content");

        // Extract headers
        List<String> headers = document.select("h1, h2, h3, h4, h5, h6")
                .stream()
                .map(Element::text)
                .collect(Collectors.toList());

        // Extract outbound links
        List<String> outboundLinks = document.select("a[href]")
                .stream()
                .map(link -> link.attr("abs:href"))
                .distinct()
                .collect(Collectors.toList());

        // Extract main content
        String mainContent = document.body().text();

        // Extract canonical URL
        String canonicalUrl = document.select("link[rel=canonical]").attr("href");

        // Extract language
        String language = document.select("html").attr("lang");

        // Create CrawledPage entity
        CrawledPage crawledPage = new CrawledPage();
        crawledPage.setUrl(url);
        crawledPage.setTitle(title);
        crawledPage.setMetaDescription(metaDescription);
        crawledPage.setKeywords(keywords);
        crawledPage.setMainContent(mainContent);
        crawledPage.setHeaders(headers);
        crawledPage.setOutboundLinks(outboundLinks);
        crawledPage.setCanonicalUrl(canonicalUrl);
        crawledPage.setLanguage(language);
        crawledPage.setHttpStatusCode(200);

        siteService.save(crawledPage);
    }
}

