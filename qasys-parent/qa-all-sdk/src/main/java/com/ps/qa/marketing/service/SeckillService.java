package com.ps.qa.marketing.service;

/**
 * @description:秒杀系统接口
 * @author:QL
 * @create：2019/08/03
 */
public interface SeckillService {
    /**
     * 秒杀
     *
     * @param userId
     * @param commId
     * @throws InterruptedException
     */
    void exchange(long userId, long commId) throws InterruptedException;
}
