package session;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import session.handlers.SessionServerHandler;

/**
 * @projectName: api-gateway
 * @package: session
 * @className: SessionChannelInitializer
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class SessionChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final Configuration configuration;

    public SessionChannelInitializer(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseDecoder());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new SessionServerHandler(configuration));
    }
}