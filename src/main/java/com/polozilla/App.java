package com.polozilla;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.List;

import static org.apache.commons.io.IOUtils.*;

/**
 * Hello world!
 *
 */
public class App 
{
    static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        for (int i=0;i<10;i ++){
            if (tryFetch()){
                break;
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private static boolean tryFetch(){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://scooby174:8082/topics");
        try {
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            int stat = response1.getStatusLine().getStatusCode();
            logger.info("status: {}",stat);
            List<String> results= readLines(response1.getEntity().getContent());
            results.forEach(it->{
                logger.info(it);

            });



            logger.info("content: {}",response1.getEntity().getContent());
            return stat == 200;
        } catch (IOException e) {
            logger.warn("Error");
            return false;
        }


    }

}
