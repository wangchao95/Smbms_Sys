package cn.jbit.smbms.dao;

import cn.jbit.smbms.entity.Role;
import cn.jbit.smbms.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色类
 */
public interface RoleDao {

    /**
     * 获得指定角色编号的所有的角色
     * @param id
     * @return
     */
    List<Role> getAllRoleList(@Param("id") Integer id);

    /**
     * 修改用户对象
     * @param role
     * @return
     */
    int editRole(Role role);

    /**
     * 根据角色名或者角色编码对象集合
     * @param roleName
     * @param roleCode
     * @return
     */
    List<Role> getRoleByParam(@Param("roleName") String roleName, @Param("roleCode") String roleCode, @Param("limitOne") Integer limitOne, @Param("pageSize") Integer pageSize);


    /**
     * 根据参数获得角色集合个数
     * @param roleName
     * @param roleCode
     * @return
     */
    int getUserCountByParma(@Param("roleName") String roleName,@Param("roleCode") String roleCode);

    /**
     * 添加角色对象
     * @param role
     * @return
     */
    int addRole(Role role);

    /**
     * 删除角色对象
     * @param role
     * @return
     */
    int delRole(@Param("id") int id);

    /**
     * 根据编码获得是否存在角色
     * @param code
     * @return
     */
    int getRoleByCode(@Param("roleCode")String code);

}
