package cn.jbit.smbms.dao;

import cn.jbit.smbms.entity.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillDao {
    /**
     * 根据条件查询订单
     * @param productName
     * @param providerId
     * @param isPayMoney
     * @return
     */
    List<Bill> getAllBillByParam(@Param("productName") String productName,@Param("providerId") Integer providerId,@Param("isPayment") Integer isPayMoney,@Param("limitOne") Integer limitOne,@Param("pageSize") Integer pageSize);

    /**
     * 获得这些条件下的订单条数
     * @param productName
     * @param providerId
     * @param isPayMoney
     * @return
     */
    int getAllBillCountByParam(@Param("productName") String productName,@Param("providerId") Integer providerId,@Param("isPayment") Integer isPayMoney);

    /**
     * 添加订单对象
     * @param bill
     * @return
     */
    int addBill(Bill bill);

    /**
     * 删除订单对象
     * @param id
     * @return
     */
    int delBillById(@Param("id") Integer id);

    /**
     * 修改订单对象
     * @param bill
     * @return
     */
    int updateBill(Bill bill);

    /**
     * 获得所有订单对象
     * @param id
     * @return
     */
    List<Bill> getBill(@Param("id") Integer id);

}
