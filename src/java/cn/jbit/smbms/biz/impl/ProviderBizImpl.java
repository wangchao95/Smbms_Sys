package cn.jbit.smbms.biz.impl;

import cn.jbit.smbms.biz.ProviderBiz;
import cn.jbit.smbms.dao.BillDao;
import cn.jbit.smbms.dao.ProviderDao;
import cn.jbit.smbms.entity.Page;
import cn.jbit.smbms.entity.Provider;
import cn.jbit.smbms.util.MybatisUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service("providerBiz")
public class ProviderBizImpl implements ProviderBiz {

    @Resource(name = "providerDao")
    private ProviderDao providerDao;

    @Resource(name="billDao")
    private BillDao billDao;
    /**
     * 获得所有的供应商集合
     * @return
     */
    public void queryAllProvider(String proCode, String proName, Page page) {
        List<Provider> providerList=new ArrayList<Provider>();
            //获得供应商的条数
       page.setTotalCount(providerDao.getAllProviderCount(proCode,proName));
        Integer limitOne=(page.getCurrPageNo()-1)*page.getPageSize();
        //获得所有的供应商集合
        page.setNewPage(providerDao.getAllProvider(proCode,proName,limitOne,page.getPageSize()));
    }

    /**
     * 获得供应商的条数
     * @param proCode
     * @param proName
     * @return
     */
    public int queryAllProviderCount(String proCode, String proName) {
        return 0;
    }

    /**
     * 获得所有的供应商集合
     * @return
     */
    public List<Provider> queryProviders(Integer id) {
        List<Provider> providerList=new ArrayList<Provider>();
        //获得所有的供应商集合
        return providerDao.getProviders(id);
    }

    public int editProviderBy(Provider provider) {
       //修改
        return providerDao.updateProvider(provider);
    }

    /**
     * 删除供应商的方法
     * @param id
     * @return
     */
    public int[] removeProvider(int id) {
        int[] message=new int[2];
        //如果此供应商在订单表中存在数据，就不能删除。
        message[1]=billDao.getAllBillCountByParam("", id, null);
        if ( message[1] > 0) {
            message[0]= -1;
        } else {
            message[0]= providerDao.deleteProvider(id);
        }
        return message;
    }

    public int addProvider(Provider provider) {
        return  providerDao.addProvider(provider);
    }
}
