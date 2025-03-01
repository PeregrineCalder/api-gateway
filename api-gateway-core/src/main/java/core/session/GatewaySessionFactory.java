package core.session;

public interface GatewaySessionFactory {
    GatewaySession openSession(String uri);
}
