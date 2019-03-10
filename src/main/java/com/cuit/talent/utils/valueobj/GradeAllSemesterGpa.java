package com.cuit.talent.utils.valueobj;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GradeAllSemesterGpa {
    private double  firstSemesterGpa;
    private double  secondSemesterGpa;
    private double  thirdlySemesterGpa;
    private double  fourthlySemesterGpa;
    private double  fifthSemesterGpa;
    private double  sixthSemesterGpa;
    private double  seventhSemesterGpa;
    private double  eighthSemesterGpa;
}
