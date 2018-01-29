package cn.jbit.smbms.controller;

import cn.jbit.smbms.biz.BillBiz;
import cn.jbit.smbms.biz.ProviderBiz;
import cn.jbit.smbms.biz.impl.BillBizImpl;
import cn.jbit.smbms.biz.impl.ProviderBizImpl;
import cn.jbit.smbms.entity.Bill;
import cn.jbit.smbms.entity.Page;
import cn.jbit.smbms.entity.Provider;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.omg.CORBA.OBJ_ADAPTER;
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
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//@RequestMapping("bill")
@Controller
public class BillController extends BaseController {

    @Resource(name = "billBiz")
    private BillBiz billBiz;
    @Resource(name = "providerBiz")
    private ProviderBiz providerBiz;

    @RequestMapping("/sys/billlist.html")
    public String getBill(Model model) {
        model.addAttribute("proList", providerBiz.queryProviders(null));
        return "billlist";
    }

//    此处的billlistPageList请求不能带有hrml后缀，不然就当成html页面解析了
    @RequestMapping(value = "/sys/billlistPageList"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object getBill(Model model, HttpServletRequest req) {
        String productName = req.getParameter("proName");
        Integer providerId = req.getParameter("providerId").equals("0") ? null : Integer.parseInt(req.getParameter("providerId"));
        Integer isPay = req.getParameter("isPay").equals("0") ? null : Integer.parseInt(req.getParameter("isPay"));
        Integer pageNo = Integer.parseInt(req.getParameter("currPageNo"));
        Page page = new Page();
        page.setCurrPageNo(pageNo);
        page.setPageSize(3);
        billBiz.findAllBillByParam(productName, providerId, isPay, page);
        String json = JSON.toJSONStringWithDateFormat(page, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        return json;
    }


    @RequestMapping(value = "/sys/billView/{id}", method = RequestMethod.GET)
    public String getBill(@PathVariable String id, Model model) {
//        查看订单对象
        List<Bill> billList = billBiz.getBill(Integer.parseInt(id));
        model.addAttribute("bill", billList.get(0));
        return "billview";
    }

    /**
     * 修改订单方法
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/sys/modifyBill/{id}", method = RequestMethod.GET)
    public String toModifyBill(@PathVariable String id, Model model) {
        List<Bill> billList = billBiz.getBill(Integer.parseInt(id));
        model.addAttribute("bill", billList.get(0));
        //获得多有的供应商.填充下拉框
        List<Provider> providerList = providerBiz.queryProviders(null);
        model.addAttribute("proList", providerList);
        return "billmodify";
    }

    @RequestMapping(value = "/sys/delBill", method = RequestMethod.GET/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object deleteBill(@RequestParam("billid") String id) {
        return JSON.toJSONString(billBiz.removeBill(Integer.parseInt(id)));
    }

    @RequestMapping("/sys/toAddBill")
    public String toAdd() {
        return "billadd";
    }

    @RequestMapping(value = "/sys/editBill"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object editBill(Bill bill,HttpServletRequest req) {
            bill.setModifyDate(new Date());
            return JSON.toJSONString(billBiz.editBill(bill));  //生成json数据
//        out.close();   //关闭资源
//        out.flush();  //刷新
    }

    /**
     * 添加订单对象
     *
     * @param req
     * @param
     * @return
     */
    @RequestMapping(value = "/sys/addBill"/*,produces = {"application/json;charset=utf-8"}*/)
    @ResponseBody
    public Object addBill(Bill bill,HttpServletRequest req) {
        bill.setCreationDate(new Date());
        return JSON.toJSONString(billBiz.addBill(bill));
    }
}
