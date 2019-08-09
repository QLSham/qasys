package com.ps.qa.service;

import com.ps.qa.domain.QasysQdetailT;
import com.ps.qa.domain.QasysUserT;
import com.ps.qa.domain.ResultVO;
import com.ps.qa.exception.BusinessException;
import com.ps.qa.mapper.InviteMapper;
import com.ps.qa.marketing.service.InviteService;
import com.ps.qa.questionsanswers.service.AskQuestionsService;
import com.ps.qa.questionsanswers.service.IntegralService;
import com.ps.qa.util.ResultEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @description:邀请用户注册送积分
 * @author:QL
 * @create：2019/07/19
 */
@Service(version = "1.0.0")
public class InviteServiceImpl implements InviteService {

    @Autowired
    private InviteMapper inviteMapper;

    @Reference(version = "1.0.0")
    private AskQuestionsService askQuestionsService;

    @Reference(version = "1.0.0")
    private IntegralService integralService;

    /**
     * 查询是否有这个验证码存在
     *
     * @param code
     * @return
     */
    @Override
    public QasysUserT inviteUser(int code) {
        QasysUserT qasysUserT = inviteMapper.inviteUser(code);
        if (null == qasysUserT) {
            throw new BusinessException(ResultEnum.AUTH_CODE_inexistence.getCode(), ResultEnum.AUTH_CODE_inexistence.getMessage());
        }
        integralService.integralWater(qasysUserT.getId(), 8, "1");
        return qasysUserT;
    }

    /**
     * 查询问卷明细
     *
     * @param themeid
     * @return
     */
    @Override
    public ResultVO queryMessage(int themeid) {
        List<QasysQdetailT> qasysQdetailTS = inviteMapper.queryMessage(themeid);
        ResultVO resultVO = new ResultVO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        resultVO.setData(qasysQdetailTS);
        return resultVO;
    }

    /**
     * 回答问卷调查
     *
     * @param answer
     * @return
     */
    @Override
    public int addQdetail(String answer, long anserId, long userId) {
        int jifen = inviteMapper.queryJiFen(anserId);
        if (answer.length() < 12) {
            throw new BusinessException(ResultEnum.ANSER_BUG.getCode(), ResultEnum.ANSER_BUG.getMessage());
        }
        int index = inviteMapper.addQdetail(answer, userId, anserId);
        integralService.integralWater(userId, jifen, "1");
        return index;
    }
}
