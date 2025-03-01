package core.authorization;

import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @projectName: api-gateway
 * @package: authorization
 * @className: GatewayAuthorizingRealm
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class GatewayAuthorizingRealm extends AuthorizingRealm {

    @Override
    public Class<? extends AuthenticationToken> getAuthenticationTokenClass() {
        return GatewayAuthorizingToken.class;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        try {
            Claims claims = JwtUtil.decode(((GatewayAuthorizingToken) authenticationToken).getJwt());
            if (!authenticationToken.getPrincipal().equals(claims.getSubject())) {throw new AuthenticationException("Invalid token");}
        } catch (Exception e) {
            throw new AuthenticationException("Invalid token", e);
        }
        return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), this.getName());
    }
}
