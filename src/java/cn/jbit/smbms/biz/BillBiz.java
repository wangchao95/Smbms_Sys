package cn.jbit.smbms.biz;

import cn.jbit.smbms.entity.Bill;
import cn.jbit.smbms.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillBiz {
    /**
     * 根据条件查询订单
     * @param productName
     * @param providerId
     * @param isPayMoney
     * @return
     */
    void findAllBillByParam(@Param("productName") String productName, @Param("providerId") Integer providerId, @Param("isPayment") Integer isPayMoney, Page page);

    /**
     * 删除订单对象
     * @param id
     * @return
     */
    int removeBill(@Param("id") Integer id);

    /**
     * 添加订单对象
     * @param bill
     * @return
     */
    int addBill(Bill bill);

    /**
     * 修改订单对象
     * @param bill
     * @return
     */
    int editBill(Bill bill);

    /**
     * 获得订单对象
     * @param id
     * @return
     */
    List<Bill> getBill(@Param("id") Integer id);
}
