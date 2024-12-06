package com.dust.search.engine.ustaad.query.processor.service;

import com.dust.search.engine.ustaad.query.processor.model.QueryResponse;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
public class QueryService {

    @Cacheable(value = "UstaadSearchCache", key = "#query")
    public QueryResponse getMyModel(String query) {
        // Simulate fetching data
        System.out.println("Fetching data for ID: " + query);
        return new QueryResponse(query, Date.from(Instant.now()), new ArrayList<Map<String, Object>>());
    }

    @CachePut(value = "UstaadSearchCache", key = "#model.query")
    public QueryResponse updateMyModel(QueryResponse model) {
        System.out.println("Updating cache for ID: " + model.getQuery());
        return model;
    }
}

