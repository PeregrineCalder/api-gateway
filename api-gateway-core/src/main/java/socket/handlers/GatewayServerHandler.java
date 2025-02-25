package socket.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapping.HttpStatement;
import session.Configuration;
import socket.BaseHandler;
import socket.agreement.AgreementConstants;
import socket.agreement.GatewayResultMessage;
import socket.agreement.RequestParser;
import socket.agreement.ResponseParser;

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
    private final Configuration configuration;

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        log.info("Gateway receive request [global] - uri：{} method：{}", request.uri(), request.method());

        try {
            RequestParser requestParser = new RequestParser(request);
            String uri = requestParser.getUri();

            HttpStatement httpStatement = configuration.getHttpStatement(uri);
            channel.attr(AgreementConstants.HTTP_STATEMENT).set(httpStatement);

            request.retain();
            ctx.fireChannelRead(request);
        } catch (Exception e) {
            DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode._500.getCode(), "Internal Server Error" + e.getMessage()));
            channel.writeAndFlush(response);
        }
    }
}
