package com.dust.search.engine.ustaad.query.processor.controller;

import com.dust.search.engine.ustaad.query.processor.model.QueryResponse;
import com.dust.search.engine.ustaad.query.processor.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @GetMapping("/model")
    public QueryResponse getModel(@RequestParam String query) {
        return queryService.getMyModel(query);
    }

    @PostMapping("/model")
    public QueryResponse updateModel(@RequestBody QueryResponse model) {
        return queryService.updateMyModel(model);
    }
}

