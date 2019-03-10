package com.cuit.talent.utils.valueobj;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserCourseSelect {
    private String courseName;
    private int grade;
    private int credit;
}
