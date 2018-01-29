package cn.jbit.smbms.biz.impl;

import cn.jbit.smbms.biz.UserBiz;
import cn.jbit.smbms.dao.AddressDao;
import cn.jbit.smbms.dao.UserDao;
import cn.jbit.smbms.entity.Page;
import cn.jbit.smbms.entity.User;
import cn.jbit.smbms.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("userBiz")
public class UserBizImpl implements UserBiz {

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "addressDao")
    private AddressDao addressDao;

    /**
     * 登录的方法
     * @param userCode
     * @param password
     * @return
     */
    public User loginUser(String userCode, String password) {
        return userDao.queryUser(new User(userCode, password));
    }

    /**
     * 根据用户变编号和用户密码判断是否存在
     * @param id
     * @param pwd
     * @return
     */
    public int findUserByIdAndPwd(Integer id, String pwd) {
        return userDao.getUserByIdAndPwd(id, pwd);
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    public int editUser(User user) {
        return userDao.editUser(user);
    }

    /**
     * 根据参数获得用户对象集合
     * @param userName
     * @param userRole
     * @param page
     * @return
     */
    public void findUserByParam(String userName, Integer userRole, Page page) {
       List<User> userList=new ArrayList<User>();
        page.setTotalCount(userDao.getUserCountByParma(userName,userRole));
        Integer limitOne=(page.getCurrPageNo()-1)*page.getPageSize();
        page.setNewPage(userDao.getUserByParam(userName,userRole,limitOne,page.getPageSize()));
    }

    /**
     * 获得用户编码是否存在
     * @param userCode
     * @return
     */
    public int getUserCountByCode(String userCode) {
       return userDao.getUserCountByCode(userCode);
    }

    /**
     * 添加用户对象
     * @param user
     * @return
     */
    public int addUser(User user) {
        return  userDao.addUser(user);
    }

    public User getUserById(Integer id) {
        return  userDao.getUserById(id);
    }

    /**
     * 删除用户的方法
     * @param id
     * @return
     */
    public int[] removeUser(Integer id) {
        int[] results=new int[2];  //一个参数接受删除的结果，第二个参数接受此用户下有地址的个数
        results[1]=addressDao.getAddressByUid(id);
       if(results[1]>0){
           results[0]=-1;
       }else{
           results[0]= userDao.delUser(id);
       }
        return results;
    }
}
