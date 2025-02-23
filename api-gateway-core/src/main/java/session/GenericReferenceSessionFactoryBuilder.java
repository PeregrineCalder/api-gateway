package session;

import io.netty.channel.Channel;
import session.defaults.GenericReferenceSessionFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @projectName: api-gateway
 * @package: session
 * @className: GenericReferenceSessionFactoryBuilder
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class GenericReferenceSessionFactoryBuilder {
    public Future<Channel> build(Configuration configuration) {
        IGenericReferenceSessionFactory genericReferenceSessionFactory = new GenericReferenceSessionFactory(configuration);
        try {
            return genericReferenceSessionFactory.openSession();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
