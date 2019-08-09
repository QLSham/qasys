package com.ps.qa.service;

import com.ps.qa.config.ThreadPoolTaskExecutorAsyncPool;
import com.ps.qa.domain.QasysCommodityT;
import com.ps.qa.domain.QasysIntegralT;
import com.ps.qa.domain.QasysUserT;
import com.ps.qa.exception.BusinessException;
import com.ps.qa.mapper.SeckillMapper;
import com.ps.qa.marketing.service.SeckillService;
import com.ps.qa.member.service.MemberService;
import com.ps.qa.questionsanswers.service.IntegralService;
import com.ps.qa.util.ResultEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @description:秒杀系统方法
 * @author:QL
 * @create：2019/08/03
 */
@Service(version = "1.0.0")
@Component
public class SeckillServiceImpl implements SeckillService {

    @Reference(version = "1.0.0")
    private MemberService memberService;

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutorAsyncPool;

    /**
     * 秒杀
     * @param userId
     * @param commId
     * @Async("pool") 数据库(连接池)
     * @throws InterruptedException
     */
    @Override
    @Async("pool")
    public void exchange(long userId, long commId) throws InterruptedException {
        // 1. 根据用户ID查询用户   (用户系统）
        QasysUserT qasysUserT = memberService.querySkill(userId);
        // 2. 根据商品ID 查询商品信息
        QasysCommodityT qasysCommodityT = seckillMapper.queryMessage(commId);
        // 3. 校验用户 （空,黑名单)
        if (null == qasysUserT) {
            redisTemplate.delete("peakClipping:kill:" + userId + "-" + commId);
            throw new BusinessException(ResultEnum.USER_NULL.getCode(), ResultEnum.USER_NULL.getMessage());
        }
        Thread.sleep(5000);
        // 4. 校验商品 (库存， 时间）
        if (0 == qasysCommodityT.getValue()) {
            redisTemplate.delete("peakClipping:kill:" + userId + "-" + commId);
            throw new BusinessException(ResultEnum.INVENTORY_NULL.getCode(), ResultEnum.INVENTORY_NULL.getMessage());
        }
        // 5. 校验用户积分是否足够
        if (qasysCommodityT.getExchangeIntegral() > qasysUserT.getIntegral()) {
            redisTemplate.delete("peakClipping:kill:" + userId + "-" + commId);
            throw new BusinessException(ResultEnum.StatusCode.getCode(), ResultEnum.StatusCode.getMessage());
        }
        // 6. 校验用户是否已经兑换过
        if (null != seckillMapper.queryOrderCommId(userId, commId)) {
            redisTemplate.delete("peakClipping:kill:" + userId + "-" + commId);
            throw new BusinessException(ResultEnum.EXCANGE_AGAIN.getCode(), ResultEnum.EXCANGE_AGAIN.getMessage());
        }
        // 7. 扣积分
        long integral = qasysUserT.getIntegral() - qasysCommodityT.getExchangeIntegral();
        // 更新积分  (记录流水)
        QasysIntegralT qasysIntegralT = memberService.updateIntegarl(userId, integral, qasysCommodityT.getExchangeIntegral(), "2");
        // 8. 减库存
        int number = qasysCommodityT.getValue() - 1;
        int temp = seckillMapper.updateNumber(commId, number, qasysCommodityT.getVersion());
        if (temp == 0) {
            // 使用kafkatemplate消息队列返回数据
            kafkaTemplate.send("quanli", "seckill-" + qasysIntegralT.getId() + "-22", "foo");
            //因为是异步,所以每次运行完都要删除掉你创建的节点
            redisTemplate.delete("peakClipping:kill:" + userId + "-" + commId);
            throw new BusinessException(ResultEnum.VERSION_NULL.getCode(), ResultEnum.VERSION_NULL.getMessage());
        }
        // 9. 创建订单
        seckillMapper.createOrder(userId, commId);
    }

}
