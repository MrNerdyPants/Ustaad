package com.dust.search.engine.ustaad.indexer.util;


import com.dust.search.engine.ustaad.indexer.enums.UrlFilterWordsAndSites;

public class UtilityService {
    public static Boolean matchFormUrlFilter(String word) {
        for (UrlFilterWordsAndSites filterWord :
                UrlFilterWordsAndSites.values()) {
            if (word.contains(filterWord.getWord())) {
                return true;
            }
        }
        return false;
    }
}
