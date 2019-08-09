package com.ps.qa.controller;

import com.ps.qa.domain.ResultVO;
import com.ps.qa.exception.BusinessException;
import com.ps.qa.marketing.service.SeckillService;
import com.ps.qa.util.ResultEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 削峰
 * @author: ql-xrh
 * @create: 2019-08-05
 */
@RestController
public class SeckillController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference(version = "1.0.0")
    private SeckillService seckillService;

    /**
     * 削峰
     *
     * @param userId
     * @param commId
     * @return
     * @throws BusinessException
     * @throws InterruptedException
     */
    @GetMapping("/exChange/{userId}/{commId}")
    public ResultVO exChange(@PathVariable Integer userId, @PathVariable Integer commId) throws BusinessException {
        // 1. 查询reids中秒杀状态  active:kill:{memberId}
        String status = (String) redisTemplate.opsForValue().get("peakClipping:kill:" + userId + "-" + commId);
        // 第一次请求     reids:1  当前人数过多
        if (status == null) {
            // 1. 排队
            boolean res = addQueue(userId, commId);
            if (res) {
                // 排队成功
                redisTemplate.opsForValue().set("peakClipping:kill:" + userId + "-" + commId, "2", 1, TimeUnit.DAYS);
                status = "2";
                System.out.println("排队成功");
            } else {
                // 排队失败
                redisTemplate.opsForValue().set("peakClipping:kill:" + userId + "-" + commId, "1", 1, TimeUnit.SECONDS);
                status = "1";
                System.out.println("排队失败");
            }
        }
        ResultVO result = null;
        if (status.equals("2")) {
            // 排队成功
            result = new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        }
        //这个就相当于限流,你排队不成功,在这个时间段之内,就不能排队,时间段过了就可以排队了
        if (status.equals("1")) {
            //排队失败
            result = new ResultVO(ResultEnum.MORE_PEOPLER.getCode(), ResultEnum.MORE_PEOPLER.getMessage());
        }
        return result;
    }

    /**
     * 兑换商品队列
     *
     * @param userId
     * @param commId
     * @return
     */
    public boolean addQueue(Integer userId, Integer commId) {
        long size = redisTemplate.opsForList().size("peakClipping:exChange");
        System.out.println("seize:" + size);
        if (size >= 10000) {
            return false;
        }
        // 返回值的判断
        Long aLong = redisTemplate.opsForList().rightPush("peakClipping:exChange", userId + "-" + commId);
        System.out.println("返回： " + aLong);
        return true;
    }

}
