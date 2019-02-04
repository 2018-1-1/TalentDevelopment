package com.cuit.talent.utils.valueobj;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Answer {
    private Integer questionBankId;
    private Object answerValue;
}
