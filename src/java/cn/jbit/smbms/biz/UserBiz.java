package cn.jbit.smbms.biz;

import cn.jbit.smbms.entity.Page;
import cn.jbit.smbms.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserBiz {
    /**
     * 登陆
     * @param userCode
     * @param password
     * @return
     */
    User loginUser(String userCode, String password);

    /**
     * 根据用户编号和密码查询是否存在此用户
     * @param id
     * @param pwd
     * @return
     */
    int findUserByIdAndPwd(@Param("id") Integer id, @Param("userPassword") String pwd);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int editUser(User user);

    /**
     * 根据用户名或者角色查找用户对象集合
     * @param userName
     * @param userRole
     * @return
     */
   void findUserByParam(@Param("userName") String userName, @Param("userRole") Integer userRole, Page page);

    /**
     * 获得此用户的编码是否存在
     * @param userCode
     * @return
     */
    int getUserCountByCode(@Param("userCode") String userCode);

    /**
     * 添加用户对象
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 获得用户对象
     * @param id
     * @return
     */
   User getUserById(@Param("id") Integer id);

    /**
     * 删除用户对象
     * @param id
     * @return
     */
   int[] removeUser(@Param("id") Integer id);

}
