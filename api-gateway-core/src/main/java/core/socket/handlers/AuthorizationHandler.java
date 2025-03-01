package core.socket.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import core.mapping.HttpStatement;
import core.session.Configuration;
import core.socket.BaseHandler;
import core.socket.agreement.AgreementConstants;
import core.socket.agreement.GatewayResultMessage;
import core.socket.agreement.ResponseParser;

/**
 * @projectName: api-gateway
 * @package: socket.handlers
 * @className: AuthorizationHandler
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
@Slf4j
public class AuthorizationHandler extends BaseHandler<FullHttpRequest> {
    private final Configuration configuration;
    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        log.info("Gateway receive request [Authorization] - uri：{} method：{}", request.uri(), request.method());
        try {
            HttpStatement httpStatement = channel.attr(AgreementConstants.HTTP_STATEMENT).get();
            if (httpStatement.isAuth()) {
                try {
                    String uId = request.headers().get("uId");
                    String token = request.headers().get("token");
                    if (null == token || token.isEmpty()) {
                        DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode._400.getCode(), "Invalid token"));
                        channel.writeAndFlush(response);
                    }
                    boolean status = configuration.authValidate(uId, token);
                    if (status) {
                        request.retain();
                        ctx.fireChannelRead(request);
                    } else {
                        DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode._403.getCode(), "Forbidden access"));
                        channel.writeAndFlush(response);
                    }
                } catch (Exception e) {
                    DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode._403.getCode(), "Invalid authorization"));
                    channel.writeAndFlush(response);
                }
            } else {
                request.retain();
                ctx.fireChannelRead(request);
            }
        } catch (Exception e) {
            DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode._500.getCode(), e.getMessage()));
            channel.writeAndFlush(response);
        }
    }
}
