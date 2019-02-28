package com.cuit.talent.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "answer_record_details", schema = "talent", catalog = "")
public class AnswerRecordDetails {
    private Integer id;
    private int optionA;
    private int optionB;
    private int optionC;
    private int optionD;
    private int optionE;
    private int optionF;
    private String fillBlankAnswer;
    private QuestionBank questionBankByQuestionBankId;
    private AnswerRecord answerRecordByAnswerRecordId;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Basic
    @Column(name = "option_a")
    public int getOptionA() {
        return optionA;
    }

    public void setOptionA(int optionA) {
        this.optionA = optionA;
    }

    @Basic
    @Column(name = "option_b")
    public int getOptionB() {
        return optionB;
    }

    public void setOptionB(int optionB) {
        this.optionB = optionB;
    }

    @Basic
    @Column(name = "option_c")
    public int getOptionC() {
        return optionC;
    }

    public void setOptionC(int optionC) {
        this.optionC = optionC;
    }

    @Basic
    @Column(name = "option_d")
    public int getOptionD() {
        return optionD;
    }

    public void setOptionD(int optionD) {
        this.optionD = optionD;
    }

    @Basic
    @Column(name = "option_e")
    public int getOptionE() {
        return optionE;
    }

    public void setOptionE(int optionE) {
        this.optionE = optionE;
    }

    @Basic
    @Column(name = "option_f")
    public int getOptionF() {
        return optionF;
    }

    public void setOptionF(int optionF) {
        this.optionF = optionF;
    }

    @Basic
    @Column(name = "fill_blank_answer")
    public String getFillBlankAnswer() {
        return fillBlankAnswer;
    }

    public void setFillBlankAnswer(String fillBlankAnswer) {
        this.fillBlankAnswer = fillBlankAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerRecordDetails that = (AnswerRecordDetails) o;
        return id == that.id &&
                optionA == that.optionA &&
                optionB == that.optionB &&
                optionC == that.optionC &&
                optionD == that.optionD &&
                optionE == that.optionE &&
                optionF == that.optionF &&
                Objects.equals(fillBlankAnswer, that.fillBlankAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, optionA, optionB, optionC, optionD, optionE, optionF, fillBlankAnswer);
    }

    @ManyToOne
    @JoinColumn(name = "question_bank_id", referencedColumnName = "id", nullable = false)
    public QuestionBank getQuestionBankByQuestionBankId() {
        return questionBankByQuestionBankId;
    }

    public void setQuestionBankByQuestionBankId(QuestionBank questionBankByQuestionBankId) {
        this.questionBankByQuestionBankId = questionBankByQuestionBankId;
    }

    @ManyToOne
    @JoinColumn(name = "answer_record_id", referencedColumnName = "id", nullable = false)
    public AnswerRecord getAnswerRecordByAnswerRecordId() {
        return answerRecordByAnswerRecordId;
    }

    public void setAnswerRecordByAnswerRecordId(AnswerRecord answerRecordByAnswerRecordId) {
        this.answerRecordByAnswerRecordId = answerRecordByAnswerRecordId;
    }
}
