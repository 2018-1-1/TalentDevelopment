package com.cuit.talent.utils.valueobj;

import com.cuit.talent.model.QuestionnaireIssue;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class QuestionnaireIssueAndFillNumber {
    QuestionnaireIssue questionnaireIssue;
    Long fillNumber;
}
