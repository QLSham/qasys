package com.ps.qa.questionsanswers.service;

import com.ps.qa.domain.QasysIntegralT;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * @description:加减积分上锁
 * @author:QL
 * @create：2019/07/21
 */
public interface IntegralService {
    /**
     * 增加用户积分
     *
     * @param userId
     * @param integral
     * @return
     * @throws InterruptedException
     * @throws IOException
     * @throws KeeperException
     */
    int addIntegral(long userId, long integral) throws InterruptedException, IOException, KeeperException;

    /**
     * 减少用户积分
     *
     * @param userId
     * @param integral
     */
    void reduceIntegral(long userId, long integral) throws KeeperException, InterruptedException, IOException;

    /**
     * 积分流水记录
     *
     * @param userid
     * @param intergral
     * @param type
     * @return
     */
    QasysIntegralT integralWater(long userid, long intergral, String type);

    /**
     * 查询用户积分表中的用户id和积分信息
     *
     * @param waterId
     * @return
     */
    QasysIntegralT queryMes(String waterId);
}
