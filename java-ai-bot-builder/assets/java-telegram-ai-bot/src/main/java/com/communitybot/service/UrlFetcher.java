package com.communitybot.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class UrlFetcher {
    public String fetchText(String url) {
        try {
            validateUrl(url);
            Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (community-bot)")
                .timeout(10_000)
                .maxBodySize(1_000_000)
                .get();
            return document.text();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to fetch content from URL: " + url, e);
        }
    }

    private void validateUrl(String url) {
        URI uri = URI.create(url);
        String scheme = uri.getScheme();
        if (scheme == null || (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))) {
            throw new IllegalArgumentException("Only http/https URLs are supported");
        }
        String host = uri.getHost();
        if (host == null || host.isBlank()) {
            throw new IllegalArgumentException("URL must include a host");
        }
        try {
            InetAddress address = InetAddress.getByName(host);
            if (address.isAnyLocalAddress() || address.isLoopbackAddress() || address.isSiteLocalAddress()) {
                throw new IllegalArgumentException("Local or private network addresses are not allowed");
            }
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Unable to resolve host: " + host, e);
        }
    }
}
