package core.socket.handlers;

import core.bind.IGenericReference;
import core.executor.result.SessionResult;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import core.session.GatewaySession;
import core.session.defaults.DefaultGatewaySessionFactory;
import core.socket.BaseHandler;
import core.socket.agreement.AgreementConstants;
import core.socket.agreement.GatewayResultMessage;
import core.socket.agreement.RequestParser;
import core.socket.agreement.ResponseParser;

import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: socket.handlers
 * @className: ProtocolDataHandler
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Slf4j
@AllArgsConstructor
public class ProtocolDataHandler extends BaseHandler<FullHttpRequest> {

    private final DefaultGatewaySessionFactory gatewaySessionFactory;

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        log.info("Gateway receive request [Message] - uri：{} method：{}", request.uri(), request.method());
        try {
            RequestParser requestParser = new RequestParser(request);
            String uri = requestParser.getUri();
            if (null == uri) return;
            Map<String, Object> args = requestParser.parse();

            GatewaySession gatewaySession = gatewaySessionFactory.openSession(uri);
            IGenericReference reference = gatewaySession.getMapper();
            SessionResult result = reference.$invoke(args);

            DefaultFullHttpResponse response = new ResponseParser().parse("0000".equals(result.getCode()) ? GatewayResultMessage.buildSuccess(result.getData()) : GatewayResultMessage.buildError(AgreementConstants.ResponseCode._404.getCode(), "Bad Gateway"));
            channel.writeAndFlush(response);
        } catch (Exception e) {
            DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode._502.getCode(), "Bad Gateway" + e.getMessage()));
            channel.writeAndFlush(response);
        }
    }
}
