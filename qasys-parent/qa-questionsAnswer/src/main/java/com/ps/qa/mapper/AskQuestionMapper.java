package com.ps.qa.mapper;

import com.ps.qa.domain.QasysIntegralT;
import com.ps.qa.domain.QasysQuestionT;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:问题
 * @author:QL
 * @create：2019/07/19
 */
@Mapper
@Repository
public interface AskQuestionMapper {
    /**
     * 查询全部问题
     *
     * @return
     */
    List<QasysQuestionT> queryQuestion();

    /**
     * 查看问题明细
     *
     * @param id 问题id
     * @return
     */
    List<QasysQuestionT> queryMessage(@Param("id") int id);

    /**
     * 发布问题
     *
     * @param user_id
     * @param question
     * @param integral
     * @return
     */
    int postQuestions(@Param("user_id") long user_id, @Param("question") String question, @Param("integral") long integral);

    /**
     * 发布问题后修改用户金额
     *
     * @param user_id
     * @param integral
     * @return
     */
    int subtractIntegral(@Param("user_id") long user_id, @Param("integral") long integral);

    /**
     * 查询用户所剩的积分
     *
     * @param user_id
     * @return
     */
    long queryUser(@Param("user_id") long user_id);

    /**
     * 回答问题
     *
     * @param user_id
     * @param answer
     * @param qid
     * @return
     */
    int questionAnswering(@Param("user_id") long user_id,
                          @Param("answer") String answer,
                          @Param("qid") long qid);

    /**
     * 先找到是谁回答的问题,得积分多少
     *
     * @param qid
     * @param userId
     * @return
     */
    List<QasysQuestionT> queryAdoptPeople(@Param("qid") long qid, @Param("userId") long userId);

    /**
     * 修改回答后的积分
     *
     * @param userId
     * @param afterIntegral
     * @return
     */
    int addIntegral(@Param("userId") long userId, @Param("afterIntegral") long afterIntegral);

    /**
     * 修改此问题是谁回答的
     *
     * @param id
     * @param userId
     * @return
     */
    int updateQuestion(@Param("userId") long userId, @Param("id") long id);

    /**
     * 增加积分流水表
     *
     * @param qasysIntegralT
     * @return
     */
    Integer integralWater(QasysIntegralT qasysIntegralT);

    /**
     * 查询积分流水表中的用户id,和积分
     *
     * @param waterId
     * @return
     */
    QasysIntegralT queryMes(String waterId);
}
