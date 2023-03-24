package com.example.cellproject.security.jwt;

import com.example.cellproject.models.Authority;
import com.example.cellproject.security.SecurityUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.KeySpec;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private String salt;

    private Key secretKey;

    private final long exp = 1000L * 60 * 60;

    private final SecurityUserDetailsService securityUserDetailsService;

    @PostConstruct // 종속성 주입이 완료된 후 실행되어야 하는 메서드
    protected void init(){
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(String usrId, List<Authority> roles) {
        Claims claims = Jwts.claims().setSubject(usrId);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 권한정보 획득
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = securityUserDetailsService.loadUserByUsername(this.getUsrId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에 담겨있는 유저Id 획득
    public String getUsrId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Authorization Header를 통해 인증을 한다.
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // Bearer 검증
            if(!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
