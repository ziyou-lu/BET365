package com.luwd.crawImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.luwd.Icrawl.ICrawl;
import com.luwd.pojos.CrawlResultPojo;
import com.luwd.pojos.UrlPojo;


/**
 *
 *  实现HTTP Get请求接口类
 * Created by luwd on 2018/08/25.
 */
public class GetCrawlerImpl implements ICrawl{
    
	public CrawlResultPojo crawl(UrlPojo urlpojo) {    
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = urlpojo.getUrl();
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("origin", "https://www.348365365.com");
            connection.setRequestProperty("Sec-WebSocket-Protocol", "zap-protocol-v1");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.92 Safari/537.36");
            connection.setRequestProperty("Accept-Language", "zh;q=0.9");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        CrawlResultPojo crawlResultPojo = new CrawlResultPojo();
        crawlResultPojo.setSuccess(true);
        crawlResultPojo.setPageContent(result);
        return crawlResultPojo;
    }
}