package com.ps.qa.controller;

import com.ps.qa.domain.ResultVO;
import com.ps.qa.questionsanswers.service.AskQuestionsService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.zookeeper.KeeperException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @description:问题
 * @author:QL
 * @create：2019/07/19
 */
@RestController
public class AskQuestionController {

    @Reference(version = "1.0.0")
    private AskQuestionsService askQuestionsService;

    /**
     * 查询全部问题
     *
     * @return
     */
    @GetMapping("/queryAsk")
    public ResultVO queryAsk() {
        return askQuestionsService.queryQuestion();
    }

    /**
     * 查询问题明细
     *
     * @return
     */
    @GetMapping("/queryAskMessage")
    public ResultVO queryAskMessage(@RequestParam("id") int id) {
        return askQuestionsService.queryMessage(id);
    }

    /**
     * 发布问题
     *
     * @param user_id
     * @param question
     * @param integral
     * @return
     */
    @GetMapping("/postQuestions")
    public ResultVO postQuestions(@RequestParam("user_id") long user_id,
                                  @RequestParam("question") String question,
                                  @RequestParam("integral") long integral) throws InterruptedException, IOException, KeeperException {
        return askQuestionsService.postQuestions(user_id, question, integral);
    }

    /**
     * 回答问题
     *
     * @param user_id
     * @param answer
     * @param qid
     * @return
     */
    @PostMapping("/questionAnswering")
    public ResultVO questionAnswering(@RequestParam("user_id") long user_id,
                                      @RequestParam("answer") String answer,
                                      @RequestParam("qid") long qid) {
        return askQuestionsService.questionAnswering(user_id, answer, qid);
    }

    /**
     * 采纳问题
     *
     * @param qid
     * @param user_id
     * @return
     */
    @PostMapping("/adoptProblem")
    public ResultVO adoptProblem(@RequestParam("qid") long qid, @RequestParam("user_id") long user_id) throws InterruptedException, IOException, KeeperException {
        return askQuestionsService.adoptProblem(qid, user_id);
    }
}