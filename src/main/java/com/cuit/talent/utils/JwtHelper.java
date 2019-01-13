package com.cuit.talent.utils;

import com.cuit.talent.utils.valueobj.JwtValue;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class JwtHelper {
    @Autowired
    private JwtValue jwtValue;

    private static Logger log=LoggerFactory.getLogger(JwtHelper.class);

    public String createToken(Integer userId, Integer roleId){
        Date nowDate = new Date();
        Date isExpiredDate = new Date(nowDate.getTime() + jwtValue.getExpireTime()*1000);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("userId", userId)
                .claim("roleId", roleId)
                .signWith(SignatureAlgorithm.HS256, jwtValue.getBase64Secret())
                .setExpiration(isExpiredDate);
        return jwtBuilder.compact();
    }

    public Claims getClaimByToken(String Token){
        if (StringUtils.isEmpty(Token)){
            return null;
        }
        try {
            return Jwts.parser().setSigningKey(jwtValue.getBase64Secret()).parseClaimsJws(Token).getBody();
        } catch (ExpiredJwtException e){
            log.info("Token过期");
        } catch (Exception e){
            log.info("Token错误");
        }
        return null;
    }

    public  boolean isTokenExpired(Date expiredDate){
        return expiredDate.before(new Date());
    }

}
