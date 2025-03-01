package core.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.session.Configuration;
import core.session.defaults.DefaultGatewaySessionFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * @projectName: api-gateway
 * @package: session
 * @className: SessionServer
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class GatewaySocketServer implements Callable<Channel> {
    private final Logger logger = LoggerFactory.getLogger(GatewaySocketServer.class);

    private final DefaultGatewaySessionFactory gatewaySessionFactory;
    private final Configuration configuration;

    // listen for connections & assign to the work group
    private EventLoopGroup boss;
    // real data read & write, business processing
    private EventLoopGroup work;

    private Channel channel;

    public GatewaySocketServer(Configuration configuration, DefaultGatewaySessionFactory gatewaySessionFactory) {
        this.configuration = configuration;
        this.gatewaySessionFactory = gatewaySessionFactory;
        this.initEventLoopGroup();
    }

    private void initEventLoopGroup() {
        boss = new NioEventLoopGroup(configuration.getBossNThreads());
        work = new NioEventLoopGroup(configuration.getWorkNThreads());
    }

    @Override
    public Channel call() {
        ChannelFuture channelFuture;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new GatewayChannelInitializer(configuration, gatewaySessionFactory));
            channelFuture = bootstrap.bind(new InetSocketAddress(configuration.getPort())).syncUninterruptibly();
            this.channel = channelFuture.channel();
        } catch (Exception e) {
            logger.error("Socket server start error", e);
        } finally {
            if (channel == null) {
                logger.error("Socket server failed to start.");
            }
        }
        return channel;
    }
}
