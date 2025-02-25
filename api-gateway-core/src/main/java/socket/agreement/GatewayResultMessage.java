package socket.agreement;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @projectName: api-gateway
 * @package: socket.agreement
 * @className: GatewayResultMessage
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
@Getter
public class GatewayResultMessage {
    private String code;
    private String info;
    private Object data;

    public static GatewayResultMessage buildSuccess(Object data) {
        return new GatewayResultMessage(AgreementConstants.ResponseCode._200.getCode(), AgreementConstants.ResponseCode._200.getInfo(), data);
    }


    public static GatewayResultMessage buildError(String code, String info) {
        return new GatewayResultMessage(code, info, null);
    }

}
