package cn.jbit.smbms.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 地址类
 */
public interface AddressDao {
    /**
     * 获得指定的用户编号的地址
     * @param id
     * @return
     */
    int getAddressByUid(@Param("userId") Integer id);
}
