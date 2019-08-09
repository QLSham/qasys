package com.ps.qa.service;

import com.ps.qa.exception.BusinessException;
import com.ps.qa.marketing.service.SeckillService;
import com.ps.qa.util.ResultEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author:QL
 * @create：2019/08/05
 */
@Component
public class XiaoFengTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutorAsyncPool;

    @Autowired
    private SeckillService seckillService;

    /**
     * 多线程
     *
     * @PostConstruct : 程序一启动就运行此方法
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void seckillGoods() {
        List<String> range = redisTemplate.opsForList().range("peakClipping:exChange", 0, 10);
        //1.判断活动是否停止  peakClipping:exChange
        if (null == range) {
            throw new BusinessException(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
        }
        for (String string : range) {
            threadPoolTaskExecutorAsyncPool.submit(() -> {
                try {
                    System.out.println("线程名字 = " + Thread.currentThread().getName());
                    System.out.println("------------------------");
                    //2.读取用户信息,开始消费
                    String[] message = string.split("-");
                    //3.开始消费(多线程)
                    seckillService.exchange(Long.valueOf(message[0]), Long.valueOf(message[1]));
                    //每正常处理一个消息,手动设置(移除)他的记录
                    redisTemplate.opsForList().remove("peakClipping:exChange" ,1,string);
                    System.out.println("成功");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
