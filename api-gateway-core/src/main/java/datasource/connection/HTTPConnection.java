package datasource.connection;

import datasource.Connection;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;


/**
 * @projectName: api-gateway
 * @package: datasource.connection
 * @className: HTTPConnection
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class HTTPConnection implements Connection {

    private final CloseableHttpClient httpClient;
    private final HttpPost httpPost;

    public HTTPConnection(String uri) {
        this.httpClient = HttpClients.createDefault();
        this.httpPost = new HttpPost(uri);

        httpPost.setHeader("Accept", "*/*");
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("Content-Type", "application/json;charset=GBK");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
    }

    @Override
    public Object execute(String method, String[] parameterTypes, String[] parameterNames, Object[] args) {
        String res = "";
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int code = response.getCode();
            if (code == 200) {
                res = new String(response.getEntity().getContent().readAllBytes());
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
