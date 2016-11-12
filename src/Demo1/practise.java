package Demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.URI;

import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.net.URISyntaxException;

/**
 * HttpClientCrawler
 * Created by xiangbo on 2016/11/12 19:41.
 */
public class practise {
    @Test
    public void test1() throws URISyntaxException {
        URI uri = new URIBuilder().setScheme("http").setHost("www.baidu.com").setPath("/search").setParameter("name", "zhangsan").setParameter("sex", "male").build();
        HttpGet httpGet = new HttpGet(uri);
        System.out.println(httpGet.getURI());
        System.out.println(uri.toString());
        System.out.println(uri);
    }

    @Test
    public void test2() {
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "ok");
        System.out.println(response.getProtocolVersion());
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getStatusLine().getReasonPhrase());
        System.out.println(response.getStatusLine().toString());
        System.out.println(response.getStatusLine());
    }

    @Test
    public void test3() {
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "ok");
        response.addHeader("Set-Cookie", "c1=a;path=/;domain=localhost");
        response.addHeader("Set-Cookie", "c2=b;path=\"/\";c3=c;domain=\"licaohost\"");
        Header header1 = response.getFirstHeader("Set-Cookie");
        System.out.println(header1);
        Header header2 = response.getLastHeader("Set-Cookie");
        System.out.println(header2);
        Header[] header = response.getAllHeaders();
        System.out.println(header.length);
        for(int i = 0; i < header.length; i++) {
            System.out.println(header[i]);
        }
        HeaderIterator it = response.headerIterator("Set-Cookie");
        while(it.hasNext()) {
            //System.out.println(it.nextHeader());
            System.out.println(it.next());
        }
    }

    @Test
    public void test4() {
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "ok");
        response.addHeader("Set-Cookie", "c1=a;path=/;domain=localhost");
        response.addHeader("Set-Cookie", "c2=b;path=\"/\";c3=c;domain=\"licaohost\"");

        HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Set-Cookie"));
        while(it.hasNext()) {
            HeaderElement headerElement = it.nextElement();
            //System.out.println(headerElement.getName() + "=" + headerElement.getValue());
            NameValuePair[] params = headerElement.getParameters();
            for(int i = 0; i < params.length; i++) {
                System.out.println(params[i].getName() + "=" + params[i].getValue());
            }
        }
    }

    @Test
    public void test5() throws IOException {
        StringEntity stringEntity = new StringEntity("important message", ContentType.create("text/html", "utf-8"));
        System.out.println(stringEntity.getContentType());
        System.out.println(stringEntity.getContentLength());
        System.out.println(stringEntity.getContentEncoding());
        System.out.println(EntityUtils.toString(stringEntity));
        System.out.println(new String(EntityUtils.toByteArray(stringEntity), "utf-8"));
        System.out.println(EntityUtils.toByteArray(stringEntity).length);
    }

    @Test
    public void test6() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        InputStream in = null;
        try {
            response = httpClient.execute(httpGet);
            if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                entity = response.getEntity();
                in = entity.getContent();
                //System.out.println(in.hashCode());
                //通过下面的hashCode可以看出同一个HttpEntity获取的输入流对象时同一个
                /*System.out.println(in.hashCode());
                in = entity.getContent();
                System.out.println(in.hashCode());*/
                //System.out.println(in.available());
                byte[] bytes = new byte[1024];
                int length = -1;
                StringBuffer sb = new StringBuffer();
                while ((length = in.read(bytes)) != -1) {
                    //System.out.println(new String(bytes, "utf-8"));
                    sb.append(new String(bytes, "utf-8"));
                }
                System.out.println("sb = " + sb.toString());
                //System.out.println(in.available());     //返回0，表示输入流已经被读完了

                //需要重新获取才可以获取
                response = httpClient.execute(httpGet);
                entity = response.getEntity();
                in = entity.getContent();
                //System.out.println(in.hashCode());
                System.out.println(in.available());     //返回0，表示输入流没有可读的内容了
                //下面的代码读不出内容，因为上面已经将in这个输入流读完了
                StringBuffer sb2 = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                String line = null;
                while((line = reader.readLine()) != null) {
                    sb2.append(line);
                }
                System.out.println("sb2 = " + sb2.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if(response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
