package com.cuit.talent.utils.valueobj;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "token")
@Component
public class JwtValue {
    private Integer expireTime;
    private String base64Secret;
}
