package socket.handlers;

import bind.IGenericReference;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import session.GatewaySession;
import session.defaults.DefaultGatewaySessionFactory;
import socket.BaseHandler;
import socket.agreement.RequestParser;
import socket.agreement.ResponseParser;

import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: socket.handlers
 * @className: GatewayServerHandler
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Slf4j
@AllArgsConstructor
public class GatewayServerHandler extends BaseHandler<FullHttpRequest> {
    private final DefaultGatewaySessionFactory gatewaySessionFactory;

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        log.info("Gateway receive request - uri：{} method：{}", request.uri(), request.method());

        RequestParser requestParser = new RequestParser(request);
        String uri = requestParser.getUri();
        if (uri == null) return;
        Map<String, Object> args = requestParser.parse();

        GatewaySession gatewaySession = gatewaySessionFactory.openSession(uri);
        IGenericReference reference = gatewaySession.getMapper();
        String result = reference.$invoke(args);

        DefaultFullHttpResponse response = new ResponseParser().parse(result);
        channel.writeAndFlush(response);
    }
}
