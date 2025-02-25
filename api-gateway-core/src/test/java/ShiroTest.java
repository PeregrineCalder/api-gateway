import authorization.IAuth;
import authorization.JwtUtil;
import authorization.auth.AuthService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: PACKAGE_NAME
 * @className: ShiroTest
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Slf4j
public class ShiroTest {

    @Test
    public void test_auth_service() {
        IAuth auth = new AuthService();
        boolean validate = auth.validate("DPij8iUY", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4aWFvZnVnZSIsImV4cCI6MTY2NDY3ODA5MCwiaWF0IjoxNjY0MDczMjkwLCJrZXkiOiJ4aWFvZnVnZSJ9.QXi2XnJGL_T0Q1vBsN8kGKV98p6xsfx6l4wAelXZp3I");
        System.out.println(validate ? "Success" : "Fail");
    }

    @Test
    public void test_awt() {
        String issuer = "peregrine";
        long ttlMillis = 7 * 24 * 60 * 60 * 1000L;
        Map<String, Object> claims = new HashMap<>();
        claims.put("key", "peregrine");

        String token = JwtUtil.encode(issuer, ttlMillis, claims);
        System.out.println(token);

        Claims parser = JwtUtil.decode(token);
        System.out.println(parser.getSubject());
    }

    @Test
    public void test_shiro() {

        IniRealm iniRealm = new IniRealm("classpath:test-shiro.ini");
        SecurityManager securityManager = new DefaultSecurityManager(iniRealm);

        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("peregrine", "123");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("Failed validation");
        }

        System.out.println(subject.isAuthenticated() ? "success validation" : "failed validation");

        subject.logout();
    }

}
