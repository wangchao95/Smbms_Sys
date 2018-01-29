package cn.jbit.smbms.dao;

import cn.jbit.smbms.entity.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProviderDao {
    /**
     * 获得指定编码或者名称的供应商对象集合
     * @return
     */
    List<Provider> getAllProvider(@Param("proCode") String proCode,@Param("proName") String proName,@Param("limitOne") Integer limitOne,@Param("pageSize") Integer pageSize);


    /**
     * 获得指定编码或者名称的供应商对象条数
     * @return
     */
    int getAllProviderCount(@Param("proCode") String proCode,@Param("proName") String proName);

    /**
     * 获得指定编号的的供应商对象集合
     * @return
     */
    List<Provider> getProviders(@Param("id") Integer id);

    /**
     * 修改供应商信息
     * @param
     * @return
     */
    int updateProvider(Provider provider);

    /**
     * 删除供应商的方法
     * @param id
     * @return
     */
    int deleteProvider(@Param("id") int id);

    /**
     * 添加供应商的方法
     * @param provider
     * @return
     */
    int addProvider(Provider provider);

}
