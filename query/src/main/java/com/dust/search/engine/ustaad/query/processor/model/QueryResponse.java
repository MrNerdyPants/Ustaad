package com.dust.search.engine.ustaad.query.processor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String query;
    private Date timeStamp;
    private List<Map<String, Object>> response;


}

