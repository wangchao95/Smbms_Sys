package cn.jbit.smbms.controller;

import cn.jbit.smbms.biz.RoleBiz;
import cn.jbit.smbms.biz.impl.RoleBizImpl;
import cn.jbit.smbms.entity.Page;
import cn.jbit.smbms.entity.Role;
import cn.jbit.smbms.entity.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Controller
public class RoleController extends BaseController{

    @Resource(name = "roleBiz")
    private RoleBiz roleBiz;

    /**
     * 填充用户的角色下拉列表
     */
    @RequestMapping(value = "/sys/roleList"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object roleSelect(){
        return JSON.toJSONString(roleBiz.findAllRole(null));
    }


    @RequestMapping("/sys/rolelist.html")
    public String toRoleJsp(){
        return "rolelist";
    }

    @RequestMapping("/sys/pageRoleList")
    @ResponseBody
    public Object getPageRole(@RequestParam("roleName") String roleName,@RequestParam("roleCode") String roleCode,
                              @RequestParam("pageIndex") String pIndex){
        Page page = new Page();
        page.setPageSize(3);
        String pageIndex = pIndex != null ? pIndex : "1";
        page.setCurrPageNo(Integer.parseInt(pageIndex));
        roleBiz.getRoleByParam(roleName,roleCode,page);
        String json = JSON.toJSONStringWithDateFormat(page,"yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue.WriteMapNullValue);
        return json;
    }

    @RequestMapping(value = "/sys/toAddRole",method = RequestMethod.GET)
    public String toAdd(){
        return "roleadd";
    }

    @RequestMapping("/sys/verifyCode")
    @ResponseBody
    public Object verifyCode(@RequestParam("roleCode") String roleCode){
       return JSON.toJSONString(roleBiz.findRoleByCode(roleCode));
    }

    /**
     * 添加角色对象
     * @return
     */
    @RequestMapping(value = "/sys/addRole",method = RequestMethod.POST)
    @ResponseBody
    public Object AddRole(Role role){
        role.setCreationDate(new Date());
        return JSON.toJSONString(roleBiz.addRole(role));
    }

    @RequestMapping(value = "/sys/toEdit/{id}",method = RequestMethod.GET)
    public String toModifyRole(@PathVariable String id, Model model){
        model.addAttribute("role",roleBiz.findAllRole(Integer.parseInt(id)).get(0));
        return "rolemodify";
    }


    @RequestMapping(value = "/sys/editRole")
    @ResponseBody
    public Object editRole(Role role){
        role.setModifyDate(new Date());
      return JSON.toJSONString(roleBiz.updateRole(role));
    }

    @RequestMapping("/sys/delrole")
    @ResponseBody
    public Object delRole(@RequestParam("rid") String rid){
      return JSON.toJSONString(roleBiz.delRole(Integer.parseInt(rid)));
    }
}
