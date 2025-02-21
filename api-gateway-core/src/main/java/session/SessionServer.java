package session;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * @projectName: api-gateway
 * @package: session
 * @className: SessionServer
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class SessionServer implements Callable<Channel> {
    private final Logger logger = LoggerFactory.getLogger(SessionServer.class);
    private final EventLoopGroup boss = new NioEventLoopGroup(1);
    private final EventLoopGroup work = new NioEventLoopGroup();
    private Channel channel;

    @Override
    public Channel call() {
        ChannelFuture channelFuture;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new SessionChannelInitializer());
            channelFuture = bootstrap.bind(new InetSocketAddress(7397)).syncUninterruptibly();
            this.channel = channelFuture.channel();
        } catch (Exception e) {
            logger.error("Socket server start error", e);
        } finally {
            logger.error("Socket server start error.");
        }
        return channel;
    }
}
