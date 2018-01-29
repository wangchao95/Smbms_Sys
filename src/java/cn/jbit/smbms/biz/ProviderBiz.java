package cn.jbit.smbms.biz;

import cn.jbit.smbms.entity.Page;
import cn.jbit.smbms.entity.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderBiz {
    /**
     * 获得所有的供应商集合
     * @return
     */
    void queryAllProvider(@Param("proCode") String proCode, @Param("proName") String proName,Page page);

    /**
     * 获得指定的编码或者名称的供应商得条数
     * @param proCode
     * @param proName
     * @return
     */
    int queryAllProviderCount(@Param("proCode") String proCode,@Param("proName") String proName);

    /**
     * 获得所有的供应商集合
     * @return
     */
    List<Provider> queryProviders(@Param("id") Integer id);


    /**
     * 修改供应商信息
     * @param provider
     * @return
     */
    int editProviderBy(Provider provider);

    /**
     * 删除供应商的方法
     * @param id
     * @return
     */
    int[] removeProvider(@Param("id") int id);

    /**
     * 添加供应商的方法
     * @param provider
     * @return
     */
    int addProvider(Provider provider);

}
