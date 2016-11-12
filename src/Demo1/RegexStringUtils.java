package Demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HttpClientCrawler
 * Created by xiangbo on 2016/11/12 14:30.
 */
public class RegexStringUtils {
    public static String regexString(String regexStr, String targetStr) {
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(targetStr);
        if(matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
