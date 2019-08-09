package com.ps.qa.service;

import com.ps.qa.domain.QasysIntegralT;
import com.ps.qa.domain.QasysUserT;
import com.ps.qa.domain.ResultVO;
import com.ps.qa.exception.BusinessException;
import com.ps.qa.mapper.MemberMapper;
import com.ps.qa.member.service.MemberService;
import com.ps.qa.questionsanswers.service.IntegralService;
import com.ps.qa.util.ResultEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description:成员
 * @author:QL
 * @create：2019/07/18
 */
@Service(version = "1.0.0")
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    /**
     * springboot集成Redis
     */
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Reference(version = "1.0.0")
    private IntegralService integralService;

    /**
     * 查询用户
     *
     * @return
     */
    @Override
    public ResultVO query() {
        ResultVO resultVO = new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        resultVO.setData(memberMapper.queryMember());
        return resultVO;
    }

    /**
     * 获取验证码
     */
    public String code() {
        int authNum1 = (int) (1 + Math.random() * 9);
        int authNum2 = (int) (1 + Math.random() * 9);
        int authNum3 = (int) (1 + Math.random() * 9);
        int authNum4 = (int) (1 + Math.random() * 9);
        String str = String.valueOf(authNum1) + String.valueOf(authNum2) + String.valueOf(authNum3) + String.valueOf(authNum4);
        return str;
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public ResultVO register(String username, String password) {
        String invitationCode = UUID.randomUUID().toString().replace("-", "");
        int register = memberMapper.register(username, password, invitationCode);
        if (register != 1) {
            throw new BusinessException(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
        }
        ResultVO resultVO = new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        if (resultVO != null) {
            kafkaTemplate.send("quanli", "username-" + username, "foo");
        }
        resultVO.setData(register);
        return resultVO;

    }

    /**
     * 增加双倍积分的时候查询该用户的信息
     *
     * @param username
     * @return
     */
    @Override
    public QasysUserT queryMessage(String username) {
        return memberMapper.queryMessage(username);
    }

    /**
     * 新注册用户加积分
     *
     * @param username
     * @param integral
     */
    @Override
    public void registerAddIntegralAgain(String username, long integral) {
        memberMapper.registerAddIntegralAgain(username, integral);
    }

    /**
     * 活动期间问题被采纳的用户信息
     *
     * @param answerUserId
     * @return
     */
    @Override
    public QasysUserT queryMessageId(String answerUserId) {
        return memberMapper.queryMessageId(answerUserId);
    }

    /**
     * 活动期间被采纳后,加积分
     *
     * @param answerUserId
     * @param all
     */
    @Override
    public void answerAddIntegral(String answerUserId, long all) {
        memberMapper.answerAddIntegral(answerUserId, all);
    }

    /**
     * 积分排序
     *
     * @return
     */
    @Override
    public ResultVO querySort() {
        ResultVO resultVO = new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        //判断是否有这个key,有就拿缓存的东西
        if (redisTemplate.hasKey("querySort")) {
            System.out.println("querySort");
            List querySort = redisTemplate.opsForList().range("querySort", 0, 10);
            resultVO.setData(querySort);
        } else {
            //没有的时候//从数据库查询数据
            List<QasysUserT> qasysUserTS = memberMapper.querySort();
            //把数据添加到Redis
            redisTemplate.opsForList().rightPushAll("querySort", qasysUserTS);
            //定时
            redisTemplate.expire("querySort", 60, TimeUnit.SECONDS);
            resultVO.setData(qasysUserTS);
        }
        return resultVO;
    }

    /**
     * 秒杀环节查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public QasysUserT querySkill(long userId) {
        return memberMapper.querySkill(userId);
    }

    /**
     * 秒杀环节 修改用户积分
     *
     * @param userId           用户id
     * @param integral         总积分
     * @param exchangeIntegral 秒杀环节的用户id
     * @param type 状态
     * @return
     */
    @Override
    public QasysIntegralT updateIntegarl(long userId, long integral, long exchangeIntegral, String type) {
        //添加积分流水表
        QasysIntegralT qasysIntegralT = integralService.integralWater(userId, exchangeIntegral, type);
        // 修改用户积分
        memberMapper.updateIntegarl(userId, integral);
        return qasysIntegralT;
    }

}
