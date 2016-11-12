package Demo1;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * HttpClientCrawler
 * Created by xiangbo on 2016/11/12 11:01.
 * 该类用来获取HttpClient对象，并使用get方式获得请求，并对请求进行处理
 */
public class HttpGetUtils {
    public static String get(String url) {
        String result = "";
        try {
            //获取HttpClient实例
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //获取方法实例
            HttpGet httpGet = new HttpGet(url);
            //执行方法得到响应
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                //如果执行正确
                if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    System.out.println(response.getStatusLine());
                    HttpEntity httpEntity = response.getEntity();
                    //从输入流中解析结果
                    result = readResponse(httpEntity, "utf-8");
                }
            } finally {
                if(httpClient != null) {
                    httpClient.close();
                }
                if(response != null) {
                    response.close();
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String readResponse(HttpEntity httpEntity, String charset) {
        StringBuffer res = new StringBuffer();
        BufferedReader reader = null;
        try {
            if(httpEntity == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), charset));
            String line = null;
            while((line = reader.readLine()) != null) {
                res.append(line);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res.toString();
    }
}
