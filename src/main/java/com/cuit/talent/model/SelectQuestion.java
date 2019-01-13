package com.cuit.talent.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "select_question", schema = "talent", catalog = "")
public class SelectQuestion {
    private int id;
    private Questionnaire questionnaireByQuestionnaireId;
    private QuestionBank questionBankByQuestionBankId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectQuestion that = (SelectQuestion) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "questionnaire_id", referencedColumnName = "id", nullable = false)
    public Questionnaire getQuestionnaireByQuestionnaireId() {
        return questionnaireByQuestionnaireId;
    }

    public void setQuestionnaireByQuestionnaireId(Questionnaire questionnaireByQuestionnaireId) {
        this.questionnaireByQuestionnaireId = questionnaireByQuestionnaireId;
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
