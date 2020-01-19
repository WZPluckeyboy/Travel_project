package cn.itcast.Travel.web.servlet;

import cn.itcast.Travel.domain.ResultInfo;
import cn.itcast.Travel.domain.User;
import cn.itcast.Travel.service.UserService;
import cn.itcast.Travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
  private   UserService service = new UserServiceImpl();
    //注册功能
   public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           ResultInfo info=new ResultInfo();
           //验证码校正
           String check=request.getParameter("check");
           // 从session中获取验证码
           HttpSession session=request.getSession();
           String  checkcodeServer= (String) session.getAttribute("CHECKCODE_SERVER");
           session.removeAttribute("CHECKCODE_SERVER");//保证验证码只是用一次
           if(checkcodeServer==null||!checkcodeServer.equalsIgnoreCase(check)){
               info.setErrorMsg("验证码错误");
               info.setFlag(false);
              writevalASString(info,response);
               return;
           }

           Map<String,String[]> map=request.getParameterMap();
           User user=new User();
           try {
               BeanUtils.populate(user,map);
           } catch (IllegalAccessException e) {
               e.printStackTrace();
           } catch (InvocationTargetException e) {
               e.printStackTrace();
           }
           boolean flag= service.regist(user);
           if(flag){
               info.setFlag(true);

           }else{
               info.setFlag(false);
               info.setErrorMsg("注册失败！");
           }
         writevalASString(info,response);
       }

       //登录功能
    public  void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户名和密码数据
        Map<String,String[]> map =  request.getParameterMap();
        //2.封装User对象
        User user =new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用Service查询
        User u= service.login(user);
        ResultInfo info=new ResultInfo();
        //4.判断用户对象是否为null
        if(u==null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        //5.判断用户是否激活
        if(u!=null&&!"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("用户尚未激活！");
        }
        //6.判断登录成功
        if(u!=null&&"Y".equals(u.getStatus())){
            info.setFlag(true);
        }
       writeVal(info,response);
    }

    //查找功能
     public  void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           //从session中获取登录用户
           Object user = request.getSession().getAttribute("user");
           //将user写回客户端
          writeVal(user,response);
       }
       //退出功能
    public  void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session
        request.getSession().invalidate();

        //2.跳转登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
      }

    //激活功能
    public  void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if(code != null){
            //2.调用service完成激活
            boolean flag = service.active(code);

            //3.判断标记
            String msg = null;
            if(flag){
                //激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            }else{
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
    }


