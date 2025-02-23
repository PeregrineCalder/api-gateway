package session.defaults;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.Configuration;
import session.IGenericReferenceSessionFactory;
import session.SessionServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @projectName: api-gateway
 * @package: session.defaults
 * @className: GenericReferenceSessionFactory
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class GenericReferenceSessionFactory implements IGenericReferenceSessionFactory {
    private final Logger logger = LoggerFactory.getLogger(GenericReferenceSessionFactory.class);

    private final Configuration configuration;

    public GenericReferenceSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public Future<Channel> openSession() throws ExecutionException, InterruptedException {
        SessionServer server = new SessionServer(configuration);

        Future<Channel> future = Executors.newFixedThreadPool(2).submit(server);
        Channel channel = future.get();
        if (null == channel) throw new RuntimeException("netty server start error channel is null");

        while (!channel.isActive()) {
            logger.info("netty server gateway start ing ...");
            Thread.sleep(500);
        }
        logger.info("netty server gateway start done! {}", channel.localAddress());

        return future;
    }
}
