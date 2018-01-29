package cn.jbit.smbms.biz.impl;

import cn.jbit.smbms.biz.RoleBiz;
import cn.jbit.smbms.dao.BillDao;
import cn.jbit.smbms.dao.RoleDao;
import cn.jbit.smbms.dao.UserDao;
import cn.jbit.smbms.entity.Page;
import cn.jbit.smbms.entity.Role;
import cn.jbit.smbms.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("roleBiz")
public class RoleBizImpl implements RoleBiz {

    @Resource(name = "roleDao")
    private RoleDao roleDao;

    @Resource(name = "userDao")
    private UserDao userDao;

    /**
     * 获得指定编号或者全部的角色对象
     * @param id
     * @return
     */
    public List<Role> findAllRole(Integer id) {
       List<Role> roleList=new ArrayList<Role>();
        return  roleDao.getAllRoleList(id);
    }

    /**
     * 修改角色对象
     * @param role
     * @return
     */
    public int updateRole(Role role) {
        return roleDao.editRole(role);
    }

    /**
     * 根据条件分页查询数据
     * @param roleName
     * @param roleCode
     * @param page
     */
    public void getRoleByParam(String roleName, String roleCode,Page page) {
        List<Role> roleList=new ArrayList<Role>();
        page.setTotalCount(roleDao.getUserCountByParma(roleName,roleCode));
        int limitOne=(page.getCurrPageNo()-1)*page.getPageSize();
        page.setNewPage(roleDao.getRoleByParam(roleName,roleCode,limitOne,page.getPageSize()));
    }

    /**
     * 添加角色对象
     * @param role
     * @return
     */
    public int addRole(Role role) {
        return roleDao.addRole(role);
    }

    /**
     * 删除角色对象
     * @param id
     * @return
     */
    public int[] delRole(int id) {
        int[] message=new int[2];
        if(userDao.getUserByRid(id)>0){
           message[0]=-1;
           message[1]=userDao.getUserByRid(id);
        }else{
            message[0]=roleDao.delRole(id);
        }
        return message;
    }

    public boolean findRoleByCode(String code) {
        return roleDao.getRoleByCode(code)>0;
    }

}
