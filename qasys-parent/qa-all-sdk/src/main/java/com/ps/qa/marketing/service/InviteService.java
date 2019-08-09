package com.ps.qa.marketing.service;

import com.ps.qa.domain.QasysUserT;
import com.ps.qa.domain.ResultVO;

/**
 * @description:邀请用户注册送积分
 * @author:QL
 * @create：2019/07/19
 */
public interface InviteService {
    /**
     * 查询是否有这个验证码的用户
     *
     * @param code
     * @return
     */
    QasysUserT inviteUser(int code);

    /**
     * 查询问卷明细
     *
     * @param themeid
     * @return
     */
    ResultVO queryMessage(int themeid);

    /**
     * 回答问题
     *
     * @param answer
     * @param anserId
     * @param userId
     * @return
     */
    int addQdetail(String answer, long anserId, long userId);
}