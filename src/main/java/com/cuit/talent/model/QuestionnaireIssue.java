package com.cuit.talent.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "questionnaire_issue", schema = "talent", catalog = "")
public class QuestionnaireIssue {
    private int id;
    private Timestamp issueTime;
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
    @Column(name = "issue_time")
    public Timestamp getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Timestamp issueTime) {
        this.issueTime = issueTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionnaireIssue that = (QuestionnaireIssue) o;
        return id == that.id &&
                Objects.equals(issueTime, that.issueTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, issueTime);
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
