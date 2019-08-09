package com.ps.qa.service;

import com.ps.qa.domain.QasysIntegralT;
import com.ps.qa.exception.BusinessException;
import com.ps.qa.mapper.AskQuestionMapper;
import com.ps.qa.questionsanswers.service.IntegralService;
import com.ps.qa.util.ResultEnum;
import com.ps.qa.util.ZkLock;
import org.apache.dubbo.config.annotation.Service;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @description:增减积分上锁
 * @author:QL
 * @create：2019/07/21
 */
@Service(version = "1.0.0")
public class IntegralServiceImpl implements IntegralService {

    @Autowired
    private AskQuestionMapper askQuestionMapper;

    /**
     * 增加积分
     *
     * @param userId
     * @param integral
     * @return
     */
    @Override
    public int addIntegral(long userId, long integral) throws InterruptedException, IOException, KeeperException {
        boolean bool;
        ZkLock zkLock = null;
        int temp = 0;
        zkLock = new ZkLock("dlamgroup");
        bool = zkLock.getLock();
        if (!bool) {
            throw new BusinessException(ResultEnum.StatusCode.getCode(), ResultEnum.StatusCode.getMessage());
        }
        //查询该用户剩余的积分
        long fen = askQuestionMapper.queryUser(userId);
        long residue = fen + integral;
        //修改操作之后的积分
        temp = askQuestionMapper.subtractIntegral(userId, residue);
        //积分流水表
        integralWater(userId, integral, "1");
        zkLock.unlock();
        return temp;
    }

    /**
     * 减少积分
     *
     * @param userId
     * @param integral
     */
    @Override
    public void reduceIntegral(long userId, long integral) throws KeeperException, InterruptedException, IOException {
        //拿锁
        boolean bool;
        ZkLock zkLock = null;
        zkLock = new ZkLock("dlamgroup");
        bool = zkLock.getLock();
        if (!bool) {
            throw new BusinessException(ResultEnum.StatusCode.getCode(), ResultEnum.StatusCode.getMessage());
        }
        //查询该用户剩余的积分
        long fen = askQuestionMapper.queryUser(userId);
        //如果积分不足以提问就抛出异常
        if (fen < integral) {
            throw new BusinessException(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
        }
        long residue = fen - integral;
        //修改操作之后的积分变动
        askQuestionMapper.subtractIntegral(userId, residue);
        //积分流水表
        integralWater(userId, integral, "2");
        zkLock.unlock();
    }

    /**
     * 积分流水表
     *
     * @param userid
     * @param intergral
     * @param type
     * @return
     */
    @Override
    public QasysIntegralT integralWater(long userid, long intergral, String type) {
        QasysIntegralT qasysIntegralT = new QasysIntegralT();
        qasysIntegralT.setUser_id(userid);
        qasysIntegralT.setIntegral(intergral);
        qasysIntegralT.setState(type);
        askQuestionMapper.integralWater(qasysIntegralT);
        return qasysIntegralT;
    }

    /**
     * 查询积分流水表中的用户id,和积分
     *
     * @param waterId
     * @return
     */
    @Override
    public QasysIntegralT queryMes(String waterId) {
        return askQuestionMapper.queryMes(waterId);
    }
}
