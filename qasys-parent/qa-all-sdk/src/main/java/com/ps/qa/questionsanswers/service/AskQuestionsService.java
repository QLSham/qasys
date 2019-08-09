package com.ps.qa.questionsanswers.service;

import com.ps.qa.domain.ResultVO;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * @description:问问题类
 * @author:QL
 * @create：2019/07/19
 */
public interface AskQuestionsService {
    /**
     * 查询全部问题
     *
     * @return
     */
    ResultVO queryQuestion();

    /**
     * 查询问题详细
     *
     * @param id
     * @return
     */
    ResultVO queryMessage(int id);

    /**
     * 发布问题
     *
     * @param user_id
     * @param question
     * @param integral
     * @return
     */
    ResultVO postQuestions(long user_id, String question, long integral) throws InterruptedException, IOException, KeeperException;

    /**
     * 回答问题
     *
     * @param user_id
     * @param answer
     * @param qid
     * @return
     */
    ResultVO questionAnswering(long user_id, String answer, long qid);

    /**
     * 采纳问题
     *
     * @param qid
     * @param userId
     * @return
     */
    ResultVO adoptProblem(long qid, long userId) throws InterruptedException, IOException, KeeperException;

}
