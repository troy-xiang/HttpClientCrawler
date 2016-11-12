package Demo1;

import java.io.IOException;

/**
 * HttpClientCrawler
 * Created by xiangbo on 2016/11/12 14:59.
 */
public class TestDownloadBaiduLogo {
    public static void main(String[] args) {
        //获取http://www.baidu.com 站点上的所有内容
        String content = HttpGetUtils.get("http://www.baidu.com");
        //定义正则表达式
        String regexStr = "hidefocus.+?src=//(.+?) width";
        //将从http://www.baidu.com 上获取的内容进行正则过滤，得到所需内容的请求网址
        String url = RegexStringUtils.regexString(regexStr, content);
        try {
            //下载过滤后所得的网址的内容
            DownloadUtils.get(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
