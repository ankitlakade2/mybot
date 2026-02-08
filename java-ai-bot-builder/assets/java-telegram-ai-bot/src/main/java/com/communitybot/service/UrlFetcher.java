package com.communitybot.service;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class UrlFetcher {
    public String fetchText(String url) {
        try {
            Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (community-bot)")
                .timeout(10_000)
                .get();
            return document.text();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to fetch content from URL: " + url, e);
        }
    }
}
