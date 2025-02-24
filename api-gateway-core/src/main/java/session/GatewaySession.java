package session;

import bind.IGenericReference;

public interface GatewaySession {
    Object get(String uri, Object parameter);

    IGenericReference getMapper();

    Configuration getConfiguration();
}
