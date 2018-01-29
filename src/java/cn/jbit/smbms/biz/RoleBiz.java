package cn.jbit.smbms.biz;

import cn.jbit.smbms.entity.Page;
import cn.jbit.smbms.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleBiz {
    /**
     * 获得指定的编号或者全部的角色对象
     * @param id
     * @return
     */
    List<Role> findAllRole(@Param("id") Integer id);

    /**
     * 修改用户对象
     * @param role
     * @return
     */
    int updateRole(Role role);

    /**
     * 根据角色名或者角色编码对象集合
     * @param roleName
     * @param roleCode
     * @return
     */
    void getRoleByParam(String roleName, String roleCode, Page page);

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
    int[] delRole(int id);

    /**
     * 获得此编码角色是否存在
     * @param code
     * @return
     */
  boolean  findRoleByCode(String code);
}
