package com.xdd.init.jwt;


import com.xdd.init.entity.SysUser;
import com.xdd.init.service.SysUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    @Autowired
    SysUserService userService;

    public Boolean filterRequest(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null && validateToken(token) && !isTokenExpired(token)) {
            Claims claims = extractAllClaims(token);
            Long userId = claims.get("userId", Long.class);
            SysUser sysUser = userService.getById(userId);
            if(sysUser!=null){
                generateRefreshToken(sysUser);
                request.setAttribute("user", sysUser);
            }
            return true;
        }
        return false;
    }

    /**
     * 生成令牌
     * @param user
     * @return
     */
    public String generateToken(SysUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phone", user.getPhonenumber());
        claims.put("userId", user.getUserId());
        return createToken(claims, user.getPhonenumber(), expiration);
    }

    /**
     * 刷新令牌
     * @param user
     * @return
     */
    public String generateRefreshToken(SysUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phone", user.getPhonenumber());
        claims.put("userId", user.getUserId());
        return createToken(claims, user.getPhonenumber(), refreshExpiration);
    }

    /**
     * 生成令牌
     * @param claims
     * @param subject
     * @param expiration
     * @return
     */
    private String createToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 验证令牌
     * @param token
     * @return
     */
    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 提取手机号
     * @param token
     * @return
     */
    public String extractPhone(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 从令牌中提取过期时间
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     *  从令牌中提取指定的声明
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从令牌中提取所有声明
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // 检查令牌是否过期
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 从HTTP请求中提取JWT令牌
     * 该方法检查请求的Authorization头是否存在，并尝试从中提取JWT令牌
     * 如果Authorization头以"Bearer "开头，则认为是一个有效的JWT令牌，并返回除去"Bearer "部分的令牌值
     * 如果Authorization头不存在或不以"Bearer "开头，则返回null
     *
     * @param request HTTP请求对象，用于获取Authorization头
     * @return 返回提取出的JWT令牌字符串，如果没有提取到有效的令牌，则返回null
     */
    public String extractToken(HttpServletRequest request) {
        // 从请求头中获取Authorization信息
        String bearerToken = request.getHeader(header);
        // 检查Authorization头是否存在并且以"Bearer "开头
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // 如果是有效的JWT令牌，则返回除去前缀"Bearer "的令牌值
            return bearerToken.substring(7);
        }
        // 如果Authorization头不存在或不以"Bearer "开头，则返回null
        return null;
    }

}
