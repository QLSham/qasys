package com.ps.qa.service;

import com.ps.qa.domain.QasysIntegralT;
import com.ps.qa.domain.QasysUserT;
import com.ps.qa.member.service.MemberService;
import com.ps.qa.questionsanswers.service.AskQuestionsService;
import com.ps.qa.questionsanswers.service.IntegralService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description:消息队列消费者
 * @author:QL
 * @create：2019/07/23
 */
@Component
public class MessageQueueConsumer {

    public static Logger logger = LoggerFactory.getLogger(MessageQueueConsumer.class);

    @Reference(version = "1.0.0")
    private MemberService memberService;

    @Reference(version = "1.0.0")
    private AskQuestionsService askQuestionsService;

    @Reference(version = "1.0.0")
    private IntegralService integralService;

    /**
     * 在规定的活动时间范围内,注册成功可以加积分
     *
     * @param cr
     */
    @KafkaListener(topics = {"quanli"})
    public void listenT1(ConsumerRecord<?, ?> cr) {
        System.out.println(cr.topic() + " ----- " + cr.key() + " ----- " + cr.value());
        String[] username = cr.key().toString().split("-");
        String u = "username";
        String a = "answer";
        String s = "seckill";
        if (username[0].equals(u)) {
            registerAddIntegralAgain(username[1]);
        } else if (username[0].equals(a)) {
            answerAddIntegral(username[1], Long.parseLong(username[2]));
        } else if (username[0].equals(s)) {
            seckillUpdateIntegral(username[1]);
        }
    }

    /**
     * 秒杀 商品不足退回积分
     *
     * @param waterId
     */
    public void seckillUpdateIntegral(String waterId) {
        //查询积分流水表中的用户id,和积分
        QasysIntegralT qasysIntegralT = integralService.queryMes(waterId);
        // 1. 根据用户ID查询用户   (用户系统）
        QasysUserT qasysUserT = memberService.querySkill(qasysIntegralT.getUser_id());
        long integral = qasysUserT.getIntegral() + qasysIntegralT.getIntegral();
        //秒杀环节 商品不足退回积分 修改用户积分 (增加积分流水表)
        memberService.updateIntegarl(qasysIntegralT.getUser_id(), integral, qasysIntegralT.getIntegral(), "1");
    }

    public void registerAddIntegralAgain(String username) {
        if (tion() == true) {
            QasysUserT qasysUserT = memberService.queryMessage(username);
            long integral = qasysUserT.getIntegral() + qasysUserT.getIntegral();
            memberService.registerAddIntegralAgain(username, integral);
        }
    }

    /**
     * 采纳问题加倍
     *
     * @param answerUserId
     * @param answerintegral
     */
    public void answerAddIntegral(String answerUserId, long answerintegral) {
        if (tion() == true) {
            QasysUserT qasysUserT = memberService.queryMessageId(answerUserId);
            long all = qasysUserT.getIntegral() + answerintegral;
            memberService.answerAddIntegral(answerUserId, all);
        }
    }

    public boolean tion() {
        String format = "yyyy-MM-dd HH:mm:ss";
        Date nowTime = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        format1.format(nowTime);
        try {
            Date startTime = new SimpleDateFormat(format).parse("2019-07-23 09:27:00");
            Date endTime = new SimpleDateFormat(format).parse("2019-07-25 09:27:59");
            if (isEffectiveDate(nowTime, startTime, endTime) != true) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

}
