package cn.jbit.smbms.controller;

import cn.jbit.smbms.biz.RoleBiz;
import cn.jbit.smbms.biz.UserBiz;
import cn.jbit.smbms.entity.Message;
import cn.jbit.smbms.entity.Page;
import cn.jbit.smbms.entity.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UserController extends BaseController {
    public static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

    @Resource(name = "userBiz")
    private UserBiz userBiz;
    @Resource(name = "roleBiz")
    private RoleBiz roleBiz;

    /**
     * 到用户管理查询角色下拉列表
     *
     * @param model
     * @return
     */
    @RequestMapping("/sys/userlist.html")
    public String toUserList(Model model) {
        model.addAttribute("roleList", roleBiz.findAllRole(null));
        return "userlist";
    }

    /**
     * 登录
     *
     * @param user
     * @param httpSession
     * @return
     */
    @RequestMapping("/saveLogin")
    public String login(User user, HttpSession httpSession) {
        User loginUser = userBiz.loginUser(user.getUserCode(), user.getUserPassword());
        if (loginUser != null && loginUser.getUserName() != null) {
            httpSession.setAttribute("user", loginUser);
            httpSession.setAttribute("birthday", DF.format(loginUser.getBirthday()));
            httpSession.setMaxInactiveInterval(600);  //设置会话失效时间
            return "frame";
        } else {
            httpSession.setAttribute("failUser", user);
            throw new RuntimeException("用户名或者密码不正确！");
        }
    }

    /**
     * 局部异常捕获方法
     *
     * @param e
     * @param re
     * @return
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public String handlerExeception(RuntimeException e, HttpServletRequest re) {
        re.setAttribute("e", e);
        return "login";
    }

    /**
     * 退出系统
     *
     * @return
     */
    @RequestMapping("outLogin")
    public String outLogin(HttpSession session) {
//        session.removeAttribute(user);
        session.invalidate();
        return "login";
    }

    /**
     * 分页查询列表
     */
    @RequestMapping(value = "/sys/userListPage"/*,produces = {"application/json;charset=utf-8"}*(这里不用配置，因为配置文件的StringHttpMessageConverter  Bean已经全局的设置了utf-8)*/)
    @ResponseBody
    public Object getPageList(HttpServletRequest req) {
        Page page = new Page();
        page.setPageSize(3);
        String name = req.getParameter("queryname");
        Integer roleId = null;
        String roleStr = req.getParameter("queryUserRole");
        if (roleStr != null && !roleStr.equals("0")) {
            roleId = Integer.parseInt(roleStr);
        }
        Integer pageIndex = req.getParameter("pageIndex") != null ? Integer.parseInt(req.getParameter("pageIndex")) : 1;
        page.setCurrPageNo(pageIndex);
        userBiz.findUserByParam(name, roleId, page);
        String json = JSON.toJSONStringWithDateFormat(page, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue.WriteMapNullValue);
        return json;
    }

    /**
     * 查看用户信息
     *
     * @return
     */
    @RequestMapping(value = "/sys/viewUser/{id}")
    public String viewUser(@PathVariable String id, Model model) {
        //获得用户编号
        model.addAttribute("user", userBiz.getUserById(Integer.parseInt(id)));
        model.addAttribute("birthday", DF.format(userBiz.getUserById(Integer.parseInt(id)).getBirthday()));
        return "userview";
    }

    @RequestMapping("/sys/toUserEdit/{id}")
    public String toEditUser(@PathVariable String id, Model model) {
        // 获得用户编号
        User user = userBiz.getUserById(Integer.parseInt(id));
        String a = DF.format(user.getBirthday());   //得到格式化时间，不然那边如果不修改时间，就会报错
        model.addAttribute("user", user);
        model.addAttribute("birthday", a);
        return "usermodify";
    }

    /**
     * 修改用户对象
     */
    @RequestMapping(value = "/sys/editUser"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object editUser(User user, HttpServletRequest req,@RequestParam(value = "Photo", required = false) MultipartFile multipartFile,@RequestParam("oldPhoto") String oldPhoto) {
        String fileName = null;  //默认的路径
        Message message=new Message();  //结果消息对象
        boolean isRight=true;
        //如果文件不为空
        if (multipartFile != null && multipartFile.getSize() > 0) {
            //这是上传图片的服务器路径
            String path = req.getSession().getServletContext().getRealPath("static" + File.separator + "uploadFiles");
            //获得源文件名的后缀
            String preFix = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            int size = 500000000;
            //如果上传的文件大于限制的大小
            if (multipartFile.getSize() > size) {
                message.setCount(-1);
                message.setMessage("上传文件尺寸太大！不得超过500000KB");
                isRight=false;
            } else if (preFix.equalsIgnoreCase("jpg") || preFix.equalsIgnoreCase("png") ||
                    preFix.equalsIgnoreCase("peng")) {
                //产生新的文件名
                 fileName = System.currentTimeMillis() + Math.round(1000) + "person.jpg";
                //创建目标文件（带文件名的）
                File targetFile = new File(path, fileName);
                if (!targetFile.exists()) {
                    targetFile.mkdir();
                }
                //保存
                try {
                    new File(path, oldPhoto).delete();   //删除源文件
                    multipartFile.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                message.setCount(-1);
                message.setMessage("上传文件格式不对");
                isRight=false;
            }
        }
        if(isRight){
            user.setIdPhoto(fileName);
            user.setModifyDate(new Date());
            message.setCount(userBiz.editUser(user));   //生成添加结果的json数zi
        }
        return JSON.toJSONString(message);
    }

    /**
     * 删除用户对象
     */
    @RequestMapping(value = "/sys/deluser"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object delUser(@RequestParam("uid") String id) {
        int[] result = userBiz.removeUser(Integer.parseInt(id));
        String delResult = "";
        //删除成啦
        if (result[0] == 1) {
            delResult = "true";
        } else if (result[0] == 0) {
            delResult = "notexist";
            //不能删除，存在地址
        } else if (result[0] == -1) {
            delResult = "existAddress";
        }
        return "{\"delResult\":\"" + delResult + "\",\"count\":\"" + result[1] + "\"}";
    }

    /**
     * 去添加用户页面
     *
     * @return
     */
    @RequestMapping("/sys/toAddUser")
    public String toAddUser() {
        return "useradd";
    }

    /**
     * 角色下拉列表
     */
    @RequestMapping("/sys/roleListSelect")
    public String roleSelectList(PrintWriter out) {
        return "redirect:roleList";
    }

    /**
     * 判断编码是否存在
     */
    @RequestMapping(value = "/sys/ucexist"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object registExist(@RequestParam("userCode") String userCode) {
        return JSON.toJSONString(userBiz.getUserCountByCode(userCode));   //获得此编码的用户是否有存在
    }

    /**
     * 添加用户对象
     */
    @RequestMapping(value = "/sys/addUser"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object addUser(User user, HttpServletRequest req, @RequestParam(value = "Photo", required = false) MultipartFile multipartFile) {
        String fileName = null;  //默认的路径
        Message message=new Message();  //结果消息对象
        boolean isRight=true;
        //如果文件不为空
        if (multipartFile != null && multipartFile.getSize() > 0) {
            //这是上传图片的服务器路径
            String path = req.getSession().getServletContext().getRealPath("static" + File.separator + "uploadFiles");
            //获得源文件名的后缀
            String preFix = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            int size = 500000000;
            //如果上传的文件大于限制的大小
            if (multipartFile.getSize() > size) {
                message.setCount(-1);
                message.setMessage("上传文件尺寸太大！不得超过500000KB");
                isRight=false;
            } else if (preFix.equalsIgnoreCase("jpg") || preFix.equalsIgnoreCase("png") ||
                    preFix.equalsIgnoreCase("peng")) {
                //产生新的文件名
                fileName = System.currentTimeMillis() + Math.round(1000) + "person.jpg";
                //创建目标文件（带文件名的）
                File targetFile = new File(path, fileName);
                if (!targetFile.exists()) {
                    targetFile.mkdir();
                }
                //保存
                try {
                    multipartFile.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                message.setCount(-1);
                message.setMessage("上传文件格式不对，必须是jpg,png,peng格式的图片");
               isRight=false;
            }
        }
        if(isRight){
            user.setIdPhoto(fileName);
            user.setCreationDate(new Date());
            message.setCount(userBiz.addUser(user));   //生成添加结果的json数zi
        }
        return JSON.toJSONString(message);
    }

    /**
     * to修改密码页面
     *
     * @return
     */
    @RequestMapping("/sys/pwdmodify.html")
    public String pwdUpdate() {
        return "pwdmodify";
    }

    /**
     * 验证原密码
     */
    @RequestMapping(value = "/sys/rigistPwd"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object registPwd(@RequestParam("oldpassword") String oldId, @RequestParam("uid") String uid) {
        return JSON.toJSONString(userBiz.findUserByIdAndPwd(Integer.parseInt(uid), oldId));
    }

    /**
     * 修改密码
     *
     * @param uid
     * @param rnewpassword
     * @param
     */
    @RequestMapping(value = "/sys/updatePwd"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object editPwd(@RequestParam("uid") String uid, @RequestParam("rnewpassword") String rnewpassword) {
        User user = new User();
        user.setId(Integer.parseInt(uid));
        user.setUserPassword(rnewpassword);
        return JSON.toJSONString(userBiz.editUser(user));
    }
}
