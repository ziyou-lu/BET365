package com.luwd.crawImpl;

import com.luwd.Icrawl.ICrawl;
import com.luwd.Level.TaskLevel;
import com.luwd.pojos.CrawlResultPojo;
import com.luwd.pojos.UrlPojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.Buffer;

/**
 * Created by lewis on 2016/10/15.
 */
public class HttpUrlConnectionCrawlerImpl implements ICrawl{            //http 抓取方式

    @Override
    public CrawlResultPojo crawl(UrlPojo urlpojo) {
        CrawlResultPojo crawlResultPojo = new CrawlResultPojo();

        if(urlpojo==null||urlpojo.getUrl()==null) {                  //若url为空，或URLpojo
            crawlResultPojo.setPageContent(null);
            crawlResultPojo.setSuccess(false);
            return crawlResultPojo;
        }

        HttpURLConnection httpURLConnection = urlpojo.getConnection();
        if(httpURLConnection!=null){
            BufferedReader bufferedReader=null;

            try {
                bufferedReader= new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
                String line =null;
                StringBuilder stringBuilder = new StringBuilder();


                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");
                }

                crawlResultPojo.setPageContent(stringBuilder.toString());
                crawlResultPojo.setSuccess(true);

                return crawlResultPojo;

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (bufferedReader!=null)
                        bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public static void main(String []args){
        System.out.println(new HttpUrlConnectionCrawlerImpl().crawl(new UrlPojo("https://www.taobao.com/", TaskLevel.HIGH)).getPageContent());
        System.out.println("done");
    }
}
