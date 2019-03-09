package com.cuit.talent.model;

public class UserCourseSelect {
    private String courseName;
    private int grade;
    private int credit;

    public void setCredit(int credit){
        this.credit = credit;
    }
    public int getCredit(){
        return credit;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
