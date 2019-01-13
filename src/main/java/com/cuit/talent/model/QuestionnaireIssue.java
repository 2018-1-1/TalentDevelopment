package com.cuit.talent.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "questionnaire_issue", schema = "talent", catalog = "")
public class QuestionnaireIssue {
    private int id;
    private Integer userId;
    private Integer questionnaireId;
    private Date issueTime;
    private User userByUserId;
    private Questionnaire questionnaireByQuestionnaireId;

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
    @Column(name = "questionnaire_id")
    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    @Basic
    @Column(name = "issue_time")
    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionnaireIssue that = (QuestionnaireIssue) o;
        return id == that.id &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(questionnaireId, that.questionnaireId) &&
                Objects.equals(issueTime, that.issueTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, questionnaireId, issueTime);
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
    @JoinColumn(name = "questionnaire_id", referencedColumnName = "id")
    public Questionnaire getQuestionnaireByQuestionnaireId() {
        return questionnaireByQuestionnaireId;
    }

    public void setQuestionnaireByQuestionnaireId(Questionnaire questionnaireByQuestionnaireId) {
        this.questionnaireByQuestionnaireId = questionnaireByQuestionnaireId;
    }
}
