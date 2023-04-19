package com.example.demojwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author SYA
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
     * 到期时间 毫秒
     */
    private static final long EXPIRE_TIME = 60 * 60 * 1000;
    private static final String USER_NAME_KEY = "userName";

    public static String createToken(String userName) {
        // 声明Token失效时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

        // 生成Token
        return JWT.create().withClaim(USER_NAME_KEY, userName).withExpiresAt(date).sign(getAlgorithm());
    }

    /**
     * 得到算法
     *
     * @return {@code Algorithm}
     */
    private static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }


    /**
     * 获得用户名
     *
     * @return {@code String}
     */
    public static String getUserName() {
        try {
            DecodedJWT jwt = JWT.decode(TokenContext.getToken());
            return jwt.getClaim(USER_NAME_KEY).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得用户名
     *
     * @param token 令牌
     * @return {@code String}
     */
    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_NAME_KEY).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 验证
     * 校验token是否正确
     *
     * @param token 密钥
     */
    public static void verify(String token) throws Exception {
        try {

            if (null == token) {
                throw new Exception("token is null");
            }

            JWTVerifier verifier = JWT.require(getAlgorithm()).build();
            DecodedJWT decodedJwt = verifier.verify(token);

            // verity 自定义参数
            String username = decodedJwt.getClaim(USER_NAME_KEY).asString();
            if (username.isBlank()) {
                throw new Exception("user is error");
            }

        } catch (TokenExpiredException e) {
            throw new Exception("token is expired");
        }
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

