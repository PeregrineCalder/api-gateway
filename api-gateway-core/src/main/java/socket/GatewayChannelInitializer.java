package socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import lombok.AllArgsConstructor;
import session.Configuration;
import session.defaults.DefaultGatewaySessionFactory;
import socket.handlers.AuthorizationHandler;
import socket.handlers.GatewayServerHandler;
import socket.handlers.ProtocolDataHandler;

/**
 * @projectName: api-gateway
 * @package: socket
 * @className: SessionChannelInitializer
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
public class GatewayChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final Configuration configuration;
    private final DefaultGatewaySessionFactory gatewaySessionFactory;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseDecoder());
        pipeline.addLast(new HttpObjectAggregator(65536));

        pipeline.addLast(new GatewayServerHandler(configuration));
        pipeline.addLast(new AuthorizationHandler(configuration));
        pipeline.addLast(new ProtocolDataHandler(gatewaySessionFactory));

    }
}