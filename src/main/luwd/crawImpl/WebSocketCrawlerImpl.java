package com.luwd.crawImpl;

import com.luwd.Icrawl.ICrawl;
import com.luwd.Level.TaskLevel;
import com.luwd.pojos.CrawlResultPojo;
import com.luwd.pojos.UrlPojo;

import java.io.*;
import java.net.Socket;

/**
 *
 *  实现接口类
 * Created by luwd on 2018/08/25.
 */
public class WebSocketCrawlerImpl implements ICrawl{                 //Socket抓取方式

    public CrawlResultPojo crawl(UrlPojo urlpojo) {                 //爬取url的内容，返回结果集合
        CrawlResultPojo crawlResultPojo = new CrawlResultPojo();

        if(urlpojo==null||urlpojo.getUrl()==null) {                  //若url为空，或URLpojo
            crawlResultPojo.setPageContent(null);
            crawlResultPojo.setSuccess(false);
            return crawlResultPojo;
        }

        String host=urlpojo.getHost();
        BufferedWriter bw = null;
        BufferedReader br = null;
        WebSocketClient socket=null;

        if(host!=null){
            try {
                /**
                 *  socket编程一般步骤
                 *（1） 创建Socket；
                 *（2） 打开连接到Socket的输入/出流；
                 *（3） 按照一定的协议对Socket进行读/写操作；
                 *（4） 关闭Socket.
                 * 其中address、host和port分别是双向连接中另一方的IP地址、主机名和端 口号，
                 * stream指明socket是流socket还是数据报socket，localPort表示本地主机的端口号，
                 * localAddr和 bindAddr是本地机器的地址（ServerSocket的主机地址）
                 * */
                socket=new Socket(host,80);
                socket.setSoTimeout(10 * 1000);
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                /**
                 * HTTP1.1
                 * 它支持持续连接.
                 * 与之相对的
                 * HTTP1.0
                 * 当连接建立起来以后,浏览器发送一个请求,之后一个回应消息被送回来.然后TCP连接被释放.
                 * 所以发生了阻塞
                 * */
                System.out.println(urlpojo.getUrl());
                System.out.println(host);
                bw.write("GET "+urlpojo.getUrl()+" HTTP/1.0\r\n");//HTTP/1.1会发生组成
                bw.write("HOST:"+host+"\r\n");
                bw.write("\r\n");//在行的结束符\r\n之前没有任何数据，代表http head输出给服务器端的数据结束并完成
                bw.flush();      //清空缓冲区
               
                System.out.println(socket.getInputStream().toString());
                br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                String line = null ;
                StringBuilder stringBuilder = new StringBuilder();
                
                
                while((line=br.readLine())!=null){
                    stringBuilder.append(line+"\n");
                }
                
 
                crawlResultPojo.setSuccess(true);
                crawlResultPojo.setPageContent(stringBuilder.toString());
                return crawlResultPojo;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket!=null)//防止出现空指针异常
                        socket.close();//释放资源，防止内存泄漏
                    if(br!=null)
                        br.close();
                    if(bw!=null)
                        bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("流关闭失败");
                }
            }
        }

        return null;
    }

    public static void main(String []args){
        CrawlerImpl cl = new CrawlerImpl();
        System.out.println(cl.crawl(new UrlPojo("https://www.348365365.com", TaskLevel.HIGH)).getPageContent());
        System.out.println("done");
    }
}
