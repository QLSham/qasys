package com.ps.qa.mapper;

import com.ps.qa.domain.QasysUserT;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:成员
 * @author:QL
 * @create：2019/07/18
 */
@Mapper
@Repository
public interface MemberMapper {

    List<QasysUserT> queryMember();

    /**
     * 注册
     *
     * @param password
     * @param username
     * @param code
     * @return
     */
    int register(@Param("password") String password, @Param("username") String username, @Param("code") String code);

    /**
     * 活动期间新注册用户信息
     *
     * @param username
     * @return
     */
    QasysUserT queryMessage(@Param("username") String username);

    /**
     * 注册成功加倍
     *
     * @param username
     * @param integral
     */
    void registerAddIntegralAgain(@Param("username") String username, @Param("integral") long integral);

    /**
     * 活动期间回答问题被采纳
     *
     * @param answerUserId
     * @return
     */
    QasysUserT queryMessageId(@Param("answerUserId") String answerUserId);

    /**
     * 采纳用户加积分
     *
     * @param answerUserId
     * @param all
     */
    void answerAddIntegral(@Param("answerUserId") String answerUserId, @Param("all") long all);

    /**
     * 查询积分排序
     *
     * @return
     */
    List<QasysUserT> querySort();

    /**
     * 秒杀环节查询用户信息
     *
     * @param userId
     * @return
     */
    QasysUserT querySkill(@Param("userId") long userId);

    /**
     * 秒杀环节 修改用户积分
     *
     * @param userId
     * @param intagral
     * @return
     */
    Integer updateIntegarl(@Param("userId") long userId, @Param("intagral") long intagral);

}
