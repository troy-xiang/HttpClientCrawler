package Demo1;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

/**
 * HttpClientCrawler
 * Created by xiangbo on 2016/11/12 14:33.
 */
public class DownloadUtils {
    public static String get(String url) throws IOException {
        String filename = "";
        String realUrl = "http://".concat(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(realUrl);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            System.out.println(response.getStatusLine());
            HttpEntity httpEntity = response.getEntity();
            filename = download(httpEntity);
        }
        httpClient.close();
        return filename;
    }

    public static String download(HttpEntity entity) {
        //图片要保存的路径
        String dirPath = "E:/img/";
        File dir = new File(dirPath);
        if (dir == null || !dir.exists()) {
            dir.mkdir();
        }
        //String.concat() Concatenates the specified string to the end of this string
        String realPath = dirPath.concat("a.png");
        File file = new File(realPath);
        if (file == null || !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //得到输入流，然后将输入流放入缓冲区
        BufferedOutputStream out = null;
        InputStream in = null;
        try {
            if (entity == null) {
                return null;
            }
            try {
                in = entity.getContent();
                out = new BufferedOutputStream(new FileOutputStream(file));
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = in.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.toString();
    }
}
