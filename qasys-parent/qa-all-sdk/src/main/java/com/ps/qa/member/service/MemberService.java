package com.ps.qa.member.service;

import com.ps.qa.domain.QasysIntegralT;
import com.ps.qa.domain.QasysUserT;
import com.ps.qa.domain.ResultVO;

/**
 * @description:成员
 * @author:QL
 * @create：2019/07/18
 */
public interface MemberService {

    /**
     * 查询用户
     *
     * @return
     */
    ResultVO query();

    /**
     * 注册
     *
     * @param username
     * @param password
     * @return
     */
    ResultVO register(String username, String password);

    /**
     * 注册送积分活动,查询此用户信息
     *
     * @param username
     * @return
     */
    QasysUserT queryMessage(String username);

    /**
     * 注册加倍送积分
     *
     * @param username
     * @param integral
     */
    void registerAddIntegralAgain(String username, long integral);

    /**
     * 活动期间回答问题被采纳的用户信息
     *
     * @param answerUserId
     * @return
     */
    QasysUserT queryMessageId(String answerUserId);

    /**
     * 被采纳的用户加倍积分
     *
     * @param answerUserId
     * @param all
     */
    void answerAddIntegral(String answerUserId, long all);

    /**
     * 查询积分排序
     *
     * @return
     */
    ResultVO querySort();

    /**
     * 秒杀环节 查询用户信息
     *
     * @param userId
     * @return
     */
    QasysUserT querySkill(long userId);

    /**
     * 秒杀环节 修改用户积分 (增加积分流水表)
     *
     * @param userId
     * @param integral
     * @param exchangeIntegral
     * @param type
     * @return
     */
    QasysIntegralT updateIntegarl(long userId, long integral, long exchangeIntegral, String type);
}
