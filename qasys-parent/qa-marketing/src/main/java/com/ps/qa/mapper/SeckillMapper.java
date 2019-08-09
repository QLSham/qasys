package com.ps.qa.mapper;

import com.ps.qa.domain.QasysCommodityT;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author:QL
 * @create：2019/08/03
 */
@Mapper
@Repository
public interface SeckillMapper {
    /**
     * 查询商品信息
     *
     * @param commId
     * @return
     */
    QasysCommodityT queryMessage(@Param("commId") long commId);

    /**
     * 判断该用户是否已经兑换过了此商品
     *
     * @param userId
     * @param commId
     * @return
     */
    String queryOrderCommId(@Param("userId") long userId, @Param("commId") long commId);

    /**
     * 修改商品库存
     *
     * @param commId
     * @param number
     * @param version
     * @return
     */
    int updateNumber(long commId, int number, int version);

    /**
     * 创建订单
     *
     * @param userId
     * @param commId
     */
    void createOrder(@Param("userId") long userId, @Param("commId") long commId);
}
