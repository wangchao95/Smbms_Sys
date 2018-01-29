package cn.jbit.smbms.controller;

import cn.jbit.smbms.biz.ProviderBiz;
import cn.jbit.smbms.biz.impl.ProviderBizImpl;
import cn.jbit.smbms.entity.Message;
import cn.jbit.smbms.entity.Page;
import cn.jbit.smbms.entity.Provider;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class ProviderController extends BaseController{

    @Resource(name = "providerBiz")
    private ProviderBiz providerBiz;

    /**
     * 下拉列表查询供应商
     * @param model
     * @param
     */
    @RequestMapping(value = "/sys/proSelect",method = RequestMethod.GET/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object deleteBill(Model model){
//        获得供应商的列表
        List<Provider> providerList=providerBiz.queryProviders(null);
        return JSON.toJSONStringWithDateFormat(providerList,"yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
    }

    /**
     *去供应商页面
     * @param model
     * @return
     */
    @RequestMapping("/sys/providerlist.html")
    public String toProviderList(Model model) {
        return "providerlist";
    }

    /**
     *供应商页面分页显示
     * @param model
     * @return
     */
    @RequestMapping(value = "/sys/providerPageList"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object ProviderList(Model model,HttpServletRequest req) {
        String proCode=req.getParameter("proCode");
        String proName=req.getParameter("proName");
        Integer pageNo=Integer.parseInt(req.getParameter("currPageNo"));
        Page page=new Page();
        page.setCurrPageNo(pageNo);
        page.setPageSize(3);
        providerBiz.queryAllProvider(proCode,proName,page);
       return  JSON.toJSONStringWithDateFormat(page,"yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
    }

    /**
     * 查询信息
     * @param id
     * @param
     * @return
     */
    @RequestMapping(value = "/sys/providerView/{id}",method = RequestMethod.GET)
    public String providerView(@PathVariable String id,Model model){
        //查看供应商信息
        List<Provider> providerList=providerBiz.queryProviders(Integer.parseInt(id));
        model.addAttribute("provider",providerList.get(0));
        return "providerview";
    }

    /**
     * 去修改页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/sys/toProviderEdit/{id}",method = RequestMethod.GET)
    public String toEditProvider(@PathVariable String id,Model model){
        //修改供应商方法
        List<Provider> providerList=providerBiz.queryProviders(Integer.parseInt(id));
        model.addAttribute("proList",providerList.get(0));
        return "providermodify";
    }

    /**
     * 修改供应商方法
     * @param req
     * @param
     */
    @RequestMapping(value = "/sys/editProvider"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object editProvider(@Valid Provider provider,BindingResult bindingResult, HttpServletRequest req,
                               @RequestParam(value = "cLicPicPath",required = false) MultipartFile multipartFile,
                               @RequestParam("oldPhoto") String oldPhoto){
        String fileName = null;  //默认的路径
        Message message = new Message();  //结果消息对象
        boolean isRight = true;
        //如果文件不为空
        if (multipartFile != null && multipartFile.getSize() > 0) {
            String path = req.getSession().getServletContext().getRealPath("static" + File.separator + "uploadFiles");
            //获得源文件名的后缀
            String preFix = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            int size = 500000000;
            //如果上传的文件大于限制的大小
            if (multipartFile.getSize() > size) {
                message.setCount(-1);
                message.setMessage("上传文件尺寸太大！不得超过500000KB");
                isRight = false;
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
                    multipartFile.transferTo(targetFile);  //把二进制的流文件传到目标文件夹，uploadFiles下面
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                message.setCount(-1);
                message.setMessage("上传文件格式不对，必须是jpg,png,peng格式的图片");
                isRight = false;
            }
        }
        //修改供应商
        //如果输入的值验证之后返回的结果有错，就返回登录页面
        if (bindingResult.hasErrors()) {
//            req.setAttribute("message",bindingResult.getAllErrors());  //得到所有的验证错误结果
            req.setAttribute("proCode", bindingResult.getAllErrors().get(0).getDefaultMessage());
            req.setAttribute("proName", bindingResult.getAllErrors().get(1).getDefaultMessage());
            req.setAttribute("proContact", bindingResult.getAllErrors().get(2).getDefaultMessage());
        } else {
            //如果为可以保存
            if (isRight) {
                provider.setComptyLicPicPath(fileName);
                provider.setModifyDate(new Date());
                message.setCount(providerBiz.editProviderBy(provider));   //生成添加结果的json数zi
            }
        }
        return JSON.toJSONString(message);
    }

    /**
     * 删除供应商对象
     * @param id
     * @param
     */
    @RequestMapping(value = "/sys/delprovider"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object delProvider(@RequestParam("proid") String id){
        //删除供应商
       int[] result= providerBiz.removeProvider(Integer.parseInt(id));
        String delResult="";
        if(result[0]==1){
            delResult="true";
        }else if(result[0]==0){
            delResult="notexist";
        }else if(result[0]==-1){
            delResult="existBill";
        }
        return "{\"delResult\":\""+delResult+"\",\"count\":\""+result[1]+"\"}";
    }
    /**
     *   到添加供应商的的页面
     */
    @RequestMapping("/sys/toAddProvider")
    public String toAddProvider(){
        return "provideradd";
    }

    /**
     * 添加供应商方法
     */
    @RequestMapping(value = "/addProvider"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object addProvider(@Valid Provider provider,BindingResult bindingResult, HttpServletRequest req, @RequestParam(value = "cLicPicPath",required = false) MultipartFile multipartFile) {
        String fileName = null;  //默认的路径
        Message message = new Message();  //结果消息对象
        boolean isRight = true;
        //如果文件不为空
        if (multipartFile != null && multipartFile.getSize() > 0) {
            String path = req.getSession().getServletContext().getRealPath("static" + File.separator + "uploadFiles");
            //获得源文件名的后缀
            String preFix = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            int size = 500000000;
            //如果上传的文件大于限制的大小
            if (multipartFile.getSize() > size) {
                message.setCount(-1);
                message.setMessage("上传文件尺寸太大！不得超过500000KB");
                isRight = false;
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
                isRight = false;
            }
        }
        //添加供应商
        //如果输入的值验证之后返回的结果有错，就返回登录页面
        if (bindingResult.hasErrors()) {
//            req.setAttribute("message",bindingResult.getAllErrors());
            req.setAttribute("proCode", bindingResult.getAllErrors().get(0).getDefaultMessage());
            bindingResult.getFieldValue("");
            req.setAttribute("proName", bindingResult.getAllErrors().get(1).getDefaultMessage());
            req.setAttribute("proContact", bindingResult.getAllErrors().get(2).getDefaultMessage());
        } else {
            //如果为可以保存
            if (isRight) {
                provider.setComptyLicPicPath(fileName);
                provider.setCreationDate(new Date());
                message.setCount(providerBiz.addProvider(provider));   //生成添加结果的json数zi
            }
        }
        return JSON.toJSONString(message);
    }
}
