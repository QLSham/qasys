package com.ps.qa.domain;

import java.io.Serializable;

/**
 * @description: 回答表
 * @author: ql-xrh
 * @create: 2019-07-18
 */
public class QasysAnswerT implements Serializable {

    private long id;
    private long user_id;
    private long qid;
    private String answer;
    private String time;
    /**
     * 问题表
     */
    private QasysQuestionT qasysQuestionT;
    /**
     * 会员id
     */
    private QasysUserT qasysUserT;

    @Override
    public String toString() {
        return "回答问题表 {" +
                "id=" + id +
                ", user_id=" + user_id +
                ", qid=" + qid +
                ", answer='" + answer + '\'' +
                ", time='" + time + '\'' +
                ", qasysQuestionT=" + qasysQuestionT +
                ", qasysUserT=" + qasysUserT +
                '}';
    }

    public QasysUserT getQasysUserT() {
        return qasysUserT;
    }

    public void setQasysUserT(QasysUserT qasysUserT) {
        this.qasysUserT = qasysUserT;
    }

    public QasysQuestionT getQasysQuestionT() {
        return qasysQuestionT;
    }

    public void setQasysQuestionT(QasysQuestionT qasysQuestionT) {
        this.qasysQuestionT = qasysQuestionT;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQid() {
        return qid;
    }

    public void setQid(long qid) {
        this.qid = qid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
