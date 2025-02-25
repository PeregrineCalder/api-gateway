package socket.agreement;

import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mapping.HttpStatement;

/**
 * @projectName: api-gateway
 * @package: socket.agreement
 * @className: AgreementConstants
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class AgreementConstants {
    public static final AttributeKey<HttpStatement> HTTP_STATEMENT = AttributeKey.valueOf("HttpStatement");

    @Getter
    @AllArgsConstructor
    public enum ResponseCode {
        _200("200","Success"),
        _400("400","Bad request"),
        _403("403","Forbidden"),
        _404("404","Not Found"),
        _500("500","Internal Server Error"),
        _502("502","Bad Gateway");

        private final String code;
        private final String info;
    }
}
