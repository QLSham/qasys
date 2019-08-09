package com.ps.qa.domain;


import java.io.Serializable;
/**
 * @description: 问卷调查结果表
 * @author: ql-xrh
 * @create: 2019-07-18
 */
public class QasysQdetailT implements Serializable {

    private long id;
    private long projectId;
    private long num;
    private String question;
    private QasysQuestionnaireT qasysQuestionnaireT;

    @Override
    public String toString() {
        return "问卷明细表 {" +
                "id=" + id +
                ", projectId=" + projectId +
                ", num=" + num +
                ", question='" + question + '\'' +
                ", qasysQuestionnaireT=" + qasysQuestionnaireT +
                '}';
    }

    public QasysQuestionnaireT getQasysQuestionnaireT() {
        return qasysQuestionnaireT;
    }

    public void setQasysQuestionnaireT(QasysQuestionnaireT qasysQuestionnaireT) {
        this.qasysQuestionnaireT = qasysQuestionnaireT;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }


    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
