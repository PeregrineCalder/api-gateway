package socket.handlers;

import bind.IGenericReference;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import session.GatewaySession;
import session.defaults.DefaultGatewaySessionFactory;
import socket.BaseHandler;

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
        String uri = request.uri();
        if (uri.equals("/favicon.ico")) return;

        GatewaySession gatewaySession = gatewaySessionFactory.openSession();
        IGenericReference reference = gatewaySession.getMapper(uri);
        String result = reference.$invoke("test") + " " + System.currentTimeMillis();

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.content().writeBytes(JSON.toJSONBytes(result, JSONWriter.Feature.PrettyFormat));

        HttpHeaders heads = response.headers();
        heads.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON + "; charset=UTF-8");
        heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        heads.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        heads.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        heads.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");
        heads.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

        channel.writeAndFlush(response);
    }
}
