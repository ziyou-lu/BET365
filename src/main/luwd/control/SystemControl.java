package com.luwd.control;

import com.luwd.Level.TaskLevel;
import com.luwd.manager.CrawlerManager;
import com.luwd.pojos.CrawlResultPojo;
import com.luwd.pojos.UrlPojo;
import com.luwd.requestType.RequestType;

import java.util.ArrayList;

/**
 *
 *
 * Created by lewis on 2016/10/15.
 */
public class SystemControl{
    public static void main(String []args){

        ArrayList<UrlPojo> urlPojoArrayList = new ArrayList<>();

        urlPojoArrayList.add(new UrlPojo("https://www.baidu.com", TaskLevel.HIGH));

        int count=0;

        for( UrlPojo urlPojo:urlPojoArrayList){
            CrawlerManager crawlerManger = new CrawlerManager(RequestType.RequestType_Socket);
            CrawlResultPojo crawlResultPojo = crawlerManger.crawl(urlPojo);
            System.out.println(crawlResultPojo.getPageContent());
            count++;
            System.out.println("已经抓取了："+count+"个页面");
        }
    }
}
