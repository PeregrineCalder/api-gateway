package socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @projectName: api-gateway
 * @package: socket
 * @className: BaseHandler
 * @author: Peregrine Calder
 * @version: 1.0
 */
public abstract class BaseHandler<T> extends SimpleChannelInboundHandler<T> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) {
        session(ctx, ctx.channel(), msg);
    }
    protected abstract void session(ChannelHandlerContext ctx, final Channel channel, T request);
}
