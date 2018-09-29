package com.luwd.manager;

import com.luwd.Icrawl.ICrawl;
import com.luwd.Level.TaskLevel;
import com.luwd.crawImpl.CrawlerImpl;
import com.luwd.crawImpl.GetCrawlerImpl;
import com.luwd.crawImpl.HttpUrlConnectionCrawlerImpl;
import com.luwd.pojos.CrawlResultPojo;
import com.luwd.pojos.UrlPojo;
import com.luwd.requestType.RequestType;

import java.net.Socket;
import java.util.Objects;

/**
 * @author luwd
 * 包含业务逻辑的抓取管理器
 * Created by luwd on 2018/09/25.
 */
public class CrawlerManager {
    private ICrawl crawler;

    public CrawlerManager(RequestType type) {
        if(type == RequestType.RequestType_Socket){
            this.crawler = new CrawlerImpl();
        }else if(type == RequestType.RequestType_Http){
            this.crawler = new HttpUrlConnectionCrawlerImpl();
        }else if(type == RequestType.RequestType_Get){
        	this.crawler = new GetCrawlerImpl();
        }
    }

    public CrawlResultPojo crawl(UrlPojo urlPojo){
        return this.crawler.crawl(urlPojo);
    }
}