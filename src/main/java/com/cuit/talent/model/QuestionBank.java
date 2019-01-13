package com.cuit.talent.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "question_bank", schema = "talent", catalog = "")
public class QuestionBank {
    private int id;
    private String problemDescription;
    private QuestionType questionTypeByQuestionTypeId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "problem_description")
    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionBank that = (QuestionBank) o;
        return id == that.id &&
                Objects.equals(problemDescription, that.problemDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, problemDescription);
    }

    @ManyToOne
    @JoinColumn(name = "question_type_id", referencedColumnName = "id", nullable = false)
    public QuestionType getQuestionTypeByQuestionTypeId() {
        return questionTypeByQuestionTypeId;
    }

    public void setQuestionTypeByQuestionTypeId(QuestionType questionTypeByQuestionTypeId) {
        this.questionTypeByQuestionTypeId = questionTypeByQuestionTypeId;
    }
}
