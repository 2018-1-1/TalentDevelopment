package com.cuit.talent.utils.valueobj;

import com.cuit.talent.model.QuestionAnswer;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RecordCount {
    private QuestionAnswer questionAnswer;
    private int count;
}
