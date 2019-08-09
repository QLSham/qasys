package com.ps.qa.service;

import com.ps.qa.domain.QasysQuestionT;
import com.ps.qa.domain.ResultVO;
import com.ps.qa.exception.BusinessException;
import com.ps.qa.mapper.AskQuestionMapper;
import com.ps.qa.questionsanswers.service.AskQuestionsService;
import com.ps.qa.questionsanswers.service.IntegralService;
import com.ps.qa.util.ResultEnum;
import org.apache.dubbo.config.annotation.Service;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.List;

/**
 * @description:问问题类
 * @author:QL
 * @create：2019/07/19
 */
@Service(version = "1.0.0")
public class AskQuestionsServiceImpl implements AskQuestionsService {

    @Autowired
    private AskQuestionMapper askQuestionMapper;

    @Autowired
    private IntegralService integralService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 查询全部的问题
     *
     * @return
     */
    @Override
    public ResultVO queryQuestion() {
        ResultVO resultVO = new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        resultVO.setData(askQuestionMapper.queryQuestion());
        return resultVO;
    }

    /**
     * 查询问题明细
     *
     * @param id
     * @return
     */
    @Override
    public ResultVO queryMessage(int id) {
        List<QasysQuestionT> list = askQuestionMapper.queryMessage(id);
        if (list == null) {
            throw new BusinessException(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
        }
        ResultVO resultVO = new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        resultVO.setData(list);
        return resultVO;
    }

    /**
     * 发布问题
     *
     * @param userId
     * @param question
     * @param integral
     * @return
     */
    @Override
    public ResultVO postQuestions(long userId, String question, long integral) throws InterruptedException, IOException, KeeperException {
        int index = askQuestionMapper.postQuestions(userId, question, integral);
        if (index != 1) {
            throw new BusinessException(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
        }
        ResultVO resultVO = new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        integralService.reduceIntegral(userId, integral);
        resultVO.setData(index);
        return resultVO;
    }

    /**
     * 回答问题
     *
     * @param userId
     * @param answer
     * @param qid
     * @return
     */
    @Override
    public ResultVO questionAnswering(long userId, String answer, long qid) {
        int index = askQuestionMapper.questionAnswering(userId, answer, qid);
        if (index != 1) {
            throw new BusinessException(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
        }
        ResultVO resultVO = new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        resultVO.setData(index);
        return resultVO;
    }

    /**
     * 采纳问题
     *
     * @param userId
     * @param qid
     * @return
     */
    @Override
    public ResultVO adoptProblem(long qid, long userId) throws InterruptedException, IOException, KeeperException {
        //先找到是谁回答的问题,得积分多少
        List<QasysQuestionT> list = askQuestionMapper.queryAdoptPeople(qid, userId);
        if (list == null) {
            throw new BusinessException(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
        }
        //这个问题的积分
        long integral = list.get(0).getIntegral();
        //回答会员加积分
        int temp = integralService.addIntegral(userId, integral);
        //修改  此问题是谁回答的
        askQuestionMapper.updateQuestion(userId, list.get(0).getId());
        System.out.println("temp = " + temp);
        if (temp != 1) {
            throw new BusinessException(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
        }
        ResultVO resultVO = new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());

        //活动时加积分
        kafkaTemplate.send("quanli", "answer-" + userId + "-" + integral, "foo");

        resultVO.setData(temp);
        integralService.integralWater(userId, integral, "1");
        return resultVO;
    }
}