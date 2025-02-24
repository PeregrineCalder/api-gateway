package session;

public interface GatewaySessionFactory {
    GatewaySession openSession(String uri);
}
