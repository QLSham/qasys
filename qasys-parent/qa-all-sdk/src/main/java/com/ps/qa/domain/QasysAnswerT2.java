package com.ps.qa.domain;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 回答表
 * @author: ql-xrh
 * @create: 2019-07-18
 */
public class QasysAnswerT2 implements Serializable {

    @Field("aId")
    private long aId;
    @Field("aQId")
    private long aQId;
    @Field("answer")
    private List<String> answer;
    @Field("aTime")
    private String aTime;

    @Override
    public String toString() {
        return "++++++ {" +
                "aId=" + aId +
                ", aQId=" + aQId +
                ", answer=" + answer +
                ", aTime='" + aTime + '\'' +
                '}';
    }

    public long getaId() {
        return aId;
    }

    public void setaId(long aId) {
        this.aId = aId;
    }

    public long getaQId() {
        return aQId;
    }

    public void setaQId(long aQId) {
        this.aQId = aQId;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public String getaTime() {
        return aTime;
    }

    public void setaTime(String aTime) {
        this.aTime = aTime;
    }
}
