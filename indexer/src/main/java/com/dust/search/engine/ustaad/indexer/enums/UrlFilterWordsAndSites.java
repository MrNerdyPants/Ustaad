package com.dust.search.engine.ustaad.crawler.enums;

import lombok.Getter;

@Getter
public enum UrlFilterWordsAndSites {
    GOOGLE("goole.com"),
    FACEBOOK("facebook.com"),
    YOUTUBE("youtube.com"),
    TWITTER("twitter.com"),
    Linkedin("linkedin.com"),
    INSTAGRAM("instagram.com"),
    WIKIPEDIA("wikipedia.org"),
    GOOGLEBLOG("googleblog.com"),
    X("x.com"),
    REDDIT("reddit.com"),
    EKIT("#ekit_modal-popup"),
    COLLAPSE("#collapse"),
    CONTENT("#content");

    UrlFilterWordsAndSites(String filterWord){
        this.word = filterWord;
    }
    private String word;

}
