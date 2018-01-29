package cn.jbit.smbms.biz.impl;

import cn.jbit.smbms.biz.BillBiz;
import cn.jbit.smbms.dao.BillDao;
import cn.jbit.smbms.entity.Bill;
import cn.jbit.smbms.entity.Page;
import cn.jbit.smbms.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service("billBiz")
public class BillBizImpl implements BillBiz {

    @Resource(name = "billDao")
    private BillDao billDao;

    public void findAllBillByParam(String productName, Integer providerId, Integer isPayMoney, Page page) {
        List<Bill> billList=new ArrayList<Bill>();
        //获得所有的订单条数
        page.setTotalCount(billDao.getAllBillCountByParam(productName,providerId,isPayMoney));
        //获得订单的集合
        Integer limitOne=(page.getCurrPageNo()-1)*page.getPageSize();
        billList= billDao.getAllBillByParam(productName,providerId,isPayMoney,limitOne,page.getPageSize());
        page.setNewPage(billList);  //设置本页的订单条数
    }

    /**
     * 删除订单对象
     * @param id
     * @return
     */
    public int removeBill(Integer id) {
        //删除的方法
        return  billDao.delBillById(id);
    }

    /**
     * 添加订单对象
     * @param bill
     * @return
     */
    public int addBill(Bill bill) {
        //插入的方法
        return  billDao.addBill(bill);
    }

    /**
     * 编辑订单对象
     * @param bill
     * @return
     */
    public int editBill(Bill bill) {
        //插入的方法
        return  billDao.updateBill(bill);
    }

    /**
     * 获得指定的订单编号的对象
     * @param id
     * @return
     */
    public List<Bill> getBill(Integer id) {
        //插入的方法
        return  billDao.getBill(id);
    }
}
