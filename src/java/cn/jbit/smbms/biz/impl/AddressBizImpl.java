package cn.jbit.smbms.biz.impl;

import cn.jbit.smbms.biz.AddressBiz;
import cn.jbit.smbms.dao.AddressDao;
import cn.jbit.smbms.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.RequestWrapper;

@Service("addressBiz")
public class AddressBizImpl implements AddressBiz {

    @Resource(name = "addressDao")
    private AddressDao addressDao;

    public int findAddressByUid(Integer id) {
        return addressDao.getAddressByUid(id);
    }
}
