package com.cuit.talent.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "question_answer", schema = "talent", catalog = "")
public class QuestionAnswer {
    private int id;
    private int questionBankId;
    private int answerKey;
    private String answerValue;
    private QuestionBank questionBankByQuestionBankId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "question_bank_id")
    public int getQuestionBankId() {
        return questionBankId;
    }

    public void setQuestionBankId(int questionBankId) {
        this.questionBankId = questionBankId;
    }

    @Basic
    @Column(name = "answer_key")
    public int getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(int answerKey) {
        this.answerKey = answerKey;
    }

    @Basic
    @Column(name = "answer_value")
    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionAnswer that = (QuestionAnswer) o;
        return id == that.id &&
                questionBankId == that.questionBankId &&
                answerKey == that.answerKey &&
                Objects.equals(answerValue, that.answerValue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, questionBankId, answerKey, answerValue);
    }

    @ManyToOne
    @JoinColumn(name = "question_bank_id", referencedColumnName = "id", nullable = false)
    public QuestionBank getQuestionBankByQuestionBankId() {
        return questionBankByQuestionBankId;
    }

    public void setQuestionBankByQuestionBankId(QuestionBank questionBankByQuestionBankId) {
        this.questionBankByQuestionBankId = questionBankByQuestionBankId;
    }
}
