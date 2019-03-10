package com.cuit.talent.utils.valueobj;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GradeGpa {
    private String grade;
    private double gpa;
    private String semester;
}
