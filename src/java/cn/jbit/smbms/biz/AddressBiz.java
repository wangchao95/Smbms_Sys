package cn.jbit.smbms.biz;

import org.apache.ibatis.annotations.Param;

public interface AddressBiz {
    /**
     * 获得指定的用户编号的地址
     * @param id
     * @return
     */
    int findAddressByUid(@Param("userId") Integer id);
}
