package com.dust.search.engine.ustaad.indexer.service;

import com.dust.search.engine.ustaad.indexer.entity.CrawledPage;
import com.dust.search.engine.ustaad.indexer.entity.IndexedPage;
import com.dust.search.engine.ustaad.indexer.repository.CrawledPageRepository;
import com.dust.search.engine.ustaad.indexer.repository.IndexedPageRepository;
import edu.stanford.nlp.simple.Sentence;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexerService {

    private final CrawledPageRepository crawledPageRepository;
    private final IndexedPageRepository indexedPageRepository;

    public IndexerService(CrawledPageRepository crawledPageRepository, IndexedPageRepository indexedPageRepository) {
        this.crawledPageRepository = crawledPageRepository;
        this.indexedPageRepository = indexedPageRepository;
    }

    public void indexPage(Long crawledPageId) {
        CrawledPage crawledPage = crawledPageRepository.findById(crawledPageId)
                .orElseThrow(() -> new IllegalArgumentException("Crawled page not found for ID: " + crawledPageId));

        if (indexedPageRepository.existsByUrl(crawledPage.getUrl())) {
            throw new IllegalArgumentException("Page already indexed: " + crawledPage.getUrl());
        }

        // Tokenize content
        List<String> tokens = tokenize(crawledPage.getMainContent());

        // Calculate token frequencies
        Map<String, Integer> tokenFrequencies = calculateTokenFrequencies(tokens);

        // Save as IndexedPage
        IndexedPage indexedPage = new IndexedPage();
        indexedPage.setUrl(crawledPage.getUrl());
        indexedPage.setTitle(crawledPage.getTitle());
        indexedPage.setMetaDescription(crawledPage.getMetaDescription());
        indexedPage.setTokens(tokenFrequencies);
        indexedPage.setLanguage(crawledPage.getLanguage());
        indexedPage.setProcessedTimestamp(Instant.now().toEpochMilli());

        indexedPageRepository.save(indexedPage);
    }

    public void indexPage(CrawledPage crawledPage) throws IOException {

        if (indexedPageRepository.existsByUrl(crawledPage.getUrl())) {
            throw new IllegalArgumentException("Page already indexed: " + crawledPage.getUrl());
        }

        // Lemitize content
        List<String> lemitizedTokens = lemmatizeText(crawledPage.getMainContent());

        // Tokenize content
        List<String> tokens = tokenize(lemitizedTokens);

        // Calculate token frequencies
        Map<String, Integer> tokenFrequencies = calculateTokenFrequencies(tokens);

        // Save as IndexedPage
        IndexedPage indexedPage = new IndexedPage();
        indexedPage.setUrl(crawledPage.getUrl());
        indexedPage.setTitle(crawledPage.getTitle());
        indexedPage.setMetaDescription(crawledPage.getMetaDescription());
        indexedPage.setTokens(tokenFrequencies);
        indexedPage.setLanguage(crawledPage.getLanguage());
        indexedPage.setProcessedTimestamp(Instant.now().toEpochMilli());

        indexedPage = indexedPageRepository.save(indexedPage);

        System.out.println(indexedPage.getId());
    }

    private static List<String> lemmatizeText(String text) {
        List<String> lemmatizedTokens = new ArrayList<>();
        Sentence sentence = new Sentence(text);

        // Stanford CoreNLP lemmatization
        lemmatizedTokens.addAll(sentence.lemmas());
        return lemmatizedTokens;
    }

    private List<String> tokenize(List<String> lemmatizedTokens) throws IOException {
        List<String> finalTokens = new ArrayList<>();

        try (StandardAnalyzer analyzer = new StandardAnalyzer()) {
            for (String lemmatizedText : lemmatizedTokens) {
                try (TokenStream tokenStream = analyzer.tokenStream("field", new StringReader(lemmatizedText))) {
                    CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                    tokenStream.reset();

                    while (tokenStream.incrementToken()) {
                        finalTokens.add(charTermAttribute.toString());
                    }

                    tokenStream.end();
                }
            }
        }

        return finalTokens;

    }
    private List<String> tokenize(String text) {
        try (Analyzer analyzer = new EnglishAnalyzer()) {
            List<String> tokens = new ArrayList<>();

            try (TokenStream tokenStream = analyzer.tokenStream("field", new StringReader(text))) {
                CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                tokenStream.reset();

                while (tokenStream.incrementToken()) {
                    tokens.add(charTermAttribute.toString());
                }

                tokenStream.end();
            }

            return tokens;
        } catch (Exception e) {
            throw new RuntimeException("Error during tokenization", e);
        }
    }

    private Map<String, Integer> calculateTokenFrequencies(List<String> tokens) {
        Map<String, Integer> tokenFrequencies = new HashMap<>();
        for (String token : tokens) {
            tokenFrequencies.put(token, tokenFrequencies.getOrDefault(token, 0) + 1);
        }
        return tokenFrequencies;
    }
}

