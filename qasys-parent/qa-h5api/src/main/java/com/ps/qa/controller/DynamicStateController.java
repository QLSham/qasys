package com.ps.qa.controller;

import com.ps.qa.domain.ResultVO;
import com.ps.qa.exception.BusinessException;
import com.ps.qa.util.ResultEnum;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description:动态url调用测试
 * @author:QL
 * @create：2019/08/04
 */
@RestController
public class DynamicStateController {

    /**
     * springboot集成Redis
     */
    @Autowired
    private RedisTemplate redisTemplate;

    public void redisNumber(String ip) {
        String key = "redisNumber_" + ip;
        Long aLong = redisTemplate.opsForList().rightPushAll(key, ip);
        if (aLong >= 6) {
            System.out.println("此用户为黑名单,不能再发送请求了");
            throw new BusinessException(ResultEnum.blacklist_people.getCode(),ResultEnum.blacklist_people.getMessage());
        }
    }

    /**
     * 动态url
     *
     * @param request
     * @return
     */
    @GetMapping("/seckill/url")
    public ResultVO getSeckillUrl(HttpServletRequest request) throws UnknownHostException {
        ResultVO resultVO = new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        String ip = request.getHeader("x-forwarded-for");
        String know = "unknown";
        String localhost = "127.0.0.1";
        String host = "0:0:0:0:0:0:0:1";
        if (ip == null || ip.length() == 0 || know.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || know.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || know.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || know.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || know.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (localhost.equals(ip) || host.equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                inet = InetAddress.getLocalHost();
                ip = inet.getHostAddress();
                System.out.println(ip);
            }
        }
        redisNumber(ip);
        sendRequest(ip);
        return resultVO;
    }

    /**
     * 动态调用url
     *
     * @param ip
     * @return
     */
    public String sendRequest(String ip) {
        String str = "http://" + ip + ":8001/test/" + test();
        System.out.println(str);
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(str);
        // 响应模型
        CloseableHttpResponse response = null;
        String content = "";
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            content = EntityUtils.toString(responseEntity);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 动态url调用返回 参数
     *
     * @param number
     */
    @RequestMapping("/test/{number}")
    public void returnValue(@PathVariable String number) {
        System.out.println("number = " + number);
    }

    /**
     * 添加定时任务
     */
    @Scheduled(cron = "0/60 * * * * ?")
    public int test() {
        int index = (int) (1 + Math.random() * 9999);
        System.out.println("index = " + index);
        return index;
    }

}
