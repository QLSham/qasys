package com.ps.qa.mapper;

import com.ps.qa.domain.QasysQdetailT;
import com.ps.qa.domain.QasysUserT;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:邀请用户注册送积分
 * @author:QL
 * @create：2019/07/19
 */
@Mapper
@Repository
public interface InviteMapper {
    /**
     * 查询是否存在这个邀请码的用户
     *
     * @param code
     * @return
     */
    QasysUserT inviteUser(@Param("code") int code);

    /**
     * 增加邀请码积分
     *
     * @param userid
     * @param jifen
     * @param type
     */
    void integralWater(@Param("userid") long userid, @Param("jifen") long jifen, @Param("type") String type);

    /**
     * 查询问卷明细
     *
     * @param themeid
     * @return
     */
    List<QasysQdetailT> queryMessage(@Param("themeid") int themeid);

    /**
     * 回答问题用户
     *
     * @param answer
     * @param userId
     * @param anserId
     * @return
     */
    int addQdetail(@Param("answer") String answer, @Param("userId") long userId,@Param("anserId")  long anserId);

    /**
     * 查询此标题的积分
     *
     * @param anserId
     * @return
     */
    int queryJiFen(@Param("anserId") long anserId);
}
