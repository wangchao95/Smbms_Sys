package cn.jbit.smbms.util;

import cn.jbit.smbms.entity.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义系统拦截器
 */
public class SysInterceptor extends HandlerInterceptorAdapter {
    /**
     * 前置方法，在执行controller的控制方法之前执行的方法，如果返回true就接着执行，如果返回false就不继续执行控制方法了
     * @return
     */
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,Object handler){
        User user = (User) req.getSession().getAttribute("user");
        if(user==null){
            try {
                //跳到没有访问权限的页面，提示登录
                resp.sendRedirect(req.getContextPath()+"/401.jsp");
                //返回false就不会执行controller接下来的控制方法了
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //接着会执行控制方法
        return true;
    }
}
