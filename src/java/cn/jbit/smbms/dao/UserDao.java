package cn.jbit.smbms.dao;

import cn.jbit.smbms.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    /**
     * 获得用户信息
     * @param user
     * @return
     */
    User queryUser(User user);

    /**
     * 根据用户编号和密码查询是否存在此用户
     * @param id
     * @param pwd
     * @return
     */
    int getUserByIdAndPwd(@Param("id") Integer id,@Param("userPassword") String pwd);

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
    List<User> getUserByParam(@Param("userName") String userName,@Param("userRole") Integer userRole,@Param("limitOne") Integer limitOne,@Param("pageSize") Integer pageSize);

    /**
     * 根据参数获得用户集合个数
     * @param userName
     * @param userRole
     * @return
     */
    int getUserCountByParma(@Param("userName") String userName,@Param("userRole") Integer userRole);

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
     * 获得用户通过编号
     * @param id
     * @return
     */
    User getUserById(@Param("id") Integer id);

    /**
     * 删除用户对象
     * @param id
     * @return
     */
    int delUser(@Param("id") Integer id);

    /**
     * 判断此角色下是否存在用户
     * @param userRole
     * @return
     */
   int  getUserByRid(@Param("userRole") int userRole);
}
