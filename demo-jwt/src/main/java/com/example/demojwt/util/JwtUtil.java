package com.example.demojwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author NDIOT-10
 * @Date 2023/4/14 11:42
 * @Description:
 */
@Component
public class JwtUtil {

    @Value("${jwt.header}")
    private String jwtHeader;
    @Value("${jwt.secret.key}")
    private String jwtSecret;


    private static String header;
    private static String secret;

    @PostConstruct
    public void init() {
        header = jwtHeader;
        secret = jwtSecret;
    }


    /**
     * 到期时间
     */
    public static final long EXPIRE_TIME = 2 * 60 * 60 * 1000;


    public static String createToken(String userName) {
        // 声明Token失效时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        // 生成Token
        return JWT.create().withClaim("userName", userName).withExpiresAt(date).sign(algorithm);
    }

    /**
     * 获得用户名
     *
     * @return {@code String}
     */
    public static String getUserName() {
        try {
            DecodedJWT jwt = JWT.decode(TokenContext.getToken());
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 验证令牌过期
     *
     * @param expirationTime 过期时间
     * @return boolean
     */
    public static boolean isTokenExpired(Date expirationTime) {
        return expirationTime.before(new Date());
    }


    public static String getHeader() {
        return header;
    }

    public static void setHeader(String header) {
        JwtUtil.header = header;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

}

