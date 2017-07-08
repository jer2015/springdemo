package cn.tom.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author lxl
 */
public class HttpTest {

    @Test
    public void Test() throws IOException, ParserException {
        // 请求客户端及参数
        CloseableHttpClient client = HttpClients.createDefault();
        // Post请求
        HttpGet httpGet = new HttpGet("http://qyxy.baic.gov.cn/wap/wap/creditWapAction!qr.dhtml?id=ff808081536011a401536476cee776bf");
        //在这里我们给Post请求的头部加上User-Agent来伪装成微信内置浏览器
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 2.3.6; zh-cn; GT-S5660 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 MicroMessenger/4.5.255");
        //这个是在网上看到的，要加上这个，避免其他错误
        httpGet.setHeader("Referer", "https://mp.weixin.qq.com");
        //发送Post请求
        CloseableHttpResponse execute = client.execute(httpGet);
        HttpEntity entity = execute.getEntity();
        String str = EntityUtils.toString(entity);
        Parser parser = new Parser();
        parser.setInputHTML(str);
        NodeFilter nodeFilter = new TagNameFilter("dd");

        NodeList list = parser.extractAllNodesThatMatch(nodeFilter);
        for (int i = 0; i < 2; i++) {
            Node node = (Node) list.elementAt(i);
            System.out.println("toPlainTextString is :" + node.toPlainTextString().replaceAll("&nbsp;", ""));
        }
    }

}
