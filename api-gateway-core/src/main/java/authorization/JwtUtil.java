package authorization;

import io.jsonwebtoken.*;
import utils.KeyLoader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: authorization
 * @className: JwtUtil
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class JwtUtil {

    private static final PrivateKey privateKey;
    private static final PublicKey publicKey;

    static {
        try {
            privateKey = KeyLoader.loadPrivateKey("private_key.pem");
            publicKey = KeyLoader.loadPublicKey("public_key.pem");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load keys", e);
        }
    }

    /**
     * Generate JWT Token String (using RSA PrivateKey)
     *
     * @param issuer    token issuer
     * @param ttlMillis valid time
     * @param claims    other info
     * @return Token
     */
    public static String encode(String issuer, long ttlMillis, Map<String, Object> claims) {
        if (claims == null) {
            claims = new HashMap<>();
        }

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .claims(claims)
                .subject(issuer)
                .issuedAt(now)
                .signWith(privateKey, Jwts.SIG.RS256);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.expiration(exp);
        }
        return builder.compact();
    }

    /**
     * Decode and verify JWT (using RSA PublicKey)
     *
     * @param token JWT Token
     * @return Claims
     */
    public static Claims decode(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
