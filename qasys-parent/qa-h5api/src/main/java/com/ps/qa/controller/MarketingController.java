package com.ps.qa.controller;

import com.ps.qa.domain.QasysUserT;
import com.ps.qa.domain.ResultVO;
import com.ps.qa.exception.BusinessException;
import com.ps.qa.marketing.service.InviteService;
import com.ps.qa.marketing.service.SeckillService;
import com.ps.qa.util.ResultEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @description:营销模块
 * @author:QL
 * @create：2019/07/19
 */
@RestController
public class MarketingController {

    @Reference(version = "1.0.0")
    private InviteService inviteService;

    @Reference(version = "1.0.0")
    private SeckillService seckillService;
    /**
     * springboot集成Redis
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询是否有这个积分
     *
     * @param code
     */
    @GetMapping("/inviteUser")
    public QasysUserT inviteUser(@RequestParam("code") int code) {
        return inviteService.inviteUser(code);
    }

    /**
     * 查询问卷明细
     *
     * @param themeId
     */
    @GetMapping("/queryMessage")
    public ResultVO queryMessage(@RequestParam("themeId") int themeId) {
        return inviteService.queryMessage(themeId);
    }

    /**
     * 回答问题
     *
     * @param answer
     * @param anserId
     * @param userId
     * @return
     */
    @GetMapping("/addQdetail")
    public int addQdetail(@RequestParam("answer") String answer,
                          @RequestParam("anserId") long anserId,
                          @RequestParam("userId") long userId) {
        return inviteService.addQdetail(answer, anserId, userId);
    }

    /**
     * 秒杀活动
     *
     * @param userId
     * @param commId
     */
    @GetMapping("/seckill")
    public void seckill(@RequestParam("userId") long userId,
                        @RequestParam("commId") long commId) throws InterruptedException {
        String key = "xianliu_" + userId + commId;
        if (redisTemplate.hasKey(key)) {
            System.out.println("限流中 ---- 请稍等");
            throw new BusinessException(ResultEnum.current_limiting.getCode(), ResultEnum.current_limiting.getMessage());
        }
        redisTemplate.opsForList().rightPushAll(key, "seckill");
        redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        seckillService.exchange(userId, commId);
    }

}
