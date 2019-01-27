package com.cuit.talent.utils.valueobj;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class AnswerDetails implements Serializable {
    private Integer questionBankId;
    private String answerKey;
    private String answerValue;
}
