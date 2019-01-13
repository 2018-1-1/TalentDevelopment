package com.cuit.talent.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "answer_record", schema = "talent", catalog = "")
public class AnswerRecord {
    private int id;
    private int userId;
    private int questionnaireIssueId;
    private int isFill;
    private User userByUserId;
    private QuestionnaireIssue questionnaireIssueByQuestionnaireIssueId;

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
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "questionnaire_issue_id")
    public int getQuestionnaireIssueId() {
        return questionnaireIssueId;
    }

    public void setQuestionnaireIssueId(int questionnaireIssueId) {
        this.questionnaireIssueId = questionnaireIssueId;
    }

    @Basic
    @Column(name = "is_fill")
    public int getIsFill() {
        return isFill;
    }

    public void setIsFill(int isFill) {
        this.isFill = isFill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerRecord that = (AnswerRecord) o;
        return id == that.id &&
                userId == that.userId &&
                questionnaireIssueId == that.questionnaireIssueId &&
                isFill == that.isFill;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, questionnaireIssueId, isFill);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "questionnaire_issue_id", referencedColumnName = "id", nullable = false)
    public QuestionnaireIssue getQuestionnaireIssueByQuestionnaireIssueId() {
        return questionnaireIssueByQuestionnaireIssueId;
    }

    public void setQuestionnaireIssueByQuestionnaireIssueId(QuestionnaireIssue questionnaireIssueByQuestionnaireIssueId) {
        this.questionnaireIssueByQuestionnaireIssueId = questionnaireIssueByQuestionnaireIssueId;
    }
}
