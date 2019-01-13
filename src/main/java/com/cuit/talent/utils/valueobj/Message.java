package com.cuit.talent.utils.valueobj;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Message {
    private String msg;

    private Integer code;

    private Object data;
}
