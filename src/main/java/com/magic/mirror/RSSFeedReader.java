package com.magic.mirror;

import com.magic.mirror.model.rest.RssFeedsOut;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
public class RSSFeedReader {

    @Value("${rss.hnonline.all}")
    private String rssUrl;

    public RssFeedsOut readRss() throws IOException, FeedException {
        URL feedSource = new URL(rssUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feedRoot = input.build(new XmlReader(feedSource));
        StringBuilder stringBuilder = new StringBuilder();
        List<SyndEntry> feeds = feedRoot.getEntries();
        for (SyndEntry feed : feeds) {
            if (feeds.indexOf(feed) == (feeds.size() - 1)) {
                stringBuilder.append(feed.getTitle()).append(".");
            } else {
                stringBuilder.append(feed.getTitle()).append(". * ");
            }
        }
        return new RssFeedsOut(stringBuilder.toString());
    }
}
