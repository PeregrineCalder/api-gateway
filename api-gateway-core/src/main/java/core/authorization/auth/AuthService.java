package core.authorization.auth;

import core.authorization.GatewayAuthorizingToken;
import core.authorization.IAuth;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * @projectName: api-gateway
 * @package: authorization.auth
 * @className: AuthService
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class AuthService implements IAuth {

    private final Subject subject;

    public AuthService() {
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        SecurityManager securityManager = new DefaultSecurityManager(iniRealm);
        SecurityUtils.setSecurityManager(securityManager);
        this.subject = SecurityUtils.getSubject();
    }

    @Override
    public boolean validate(String id, String token) {
        try {
            subject.login(new GatewayAuthorizingToken(id, token));
            return subject.isAuthenticated();
        } finally {
            subject.logout();
        }

    }
}
