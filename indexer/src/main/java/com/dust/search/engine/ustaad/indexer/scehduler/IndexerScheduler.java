package com.dust.search.engine.ustaad.indexer.scehduler;

import com.dust.search.engine.ustaad.indexer.entity.CrawledPage;
import com.dust.search.engine.ustaad.indexer.service.CrawledPageService;
import com.dust.search.engine.ustaad.indexer.service.IndexerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@EnableAsync
public class IndexerScheduler {

    @Autowired
    IndexerService indexerService;

    @Autowired
    CrawledPageService crawledPageService;

    @Async
    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException, IOException {
        List<CrawledPage> crawledPageList = crawledPageService.consume();
        for (CrawledPage page :
                crawledPageList) {
            try {
                indexerService.indexPage(page);
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }

        }
//        System.out.println(
//                "Fixed rate task async - " + System.currentTimeMillis() / 1000);
//        Thread.sleep(2000);
    }
}
