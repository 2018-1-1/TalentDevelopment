package com.cuit.talent.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_grade", schema = "talent", catalog = "")
public class UserGrade {
    private int id;
    private Integer userId;
    private Integer gradeId;
    private User userByUserId;
    private Grade gradeByGradeId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "grade_id")
    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGrade userGrade = (UserGrade) o;
        return id == userGrade.id &&
                Objects.equals(userId, userGrade.userId) &&
                Objects.equals(gradeId, userGrade.gradeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, gradeId);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    public Grade getGradeByGradeId() {
        return gradeByGradeId;
    }

    public void setGradeByGradeId(Grade gradeByGradeId) {
        this.gradeByGradeId = gradeByGradeId;
    }
}
