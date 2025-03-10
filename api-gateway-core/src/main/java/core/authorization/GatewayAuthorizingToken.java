package core.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @projectName: api-gateway
 * @package: authorization
 * @className: GatewayAuthorizingToken
 * @author: Peregrine Calder
 * @version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GatewayAuthorizingToken implements AuthenticationToken {
    private String uid;

    private String jwt;

    @Override
    public Object getPrincipal() {
        return uid;
    }

    @Override
    public Object getCredentials() {
        return this.jwt;
    }
}
