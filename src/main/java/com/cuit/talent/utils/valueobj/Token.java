package com.cuit.talent.utils.valueobj;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Token {
    private String token;

    private String expiredTime;

}
