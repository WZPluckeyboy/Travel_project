package cn.itcast.Travel.web.servlet.未合并时数据;

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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
     UserService userService=new UserServiceImpl();
      User u= userService.login(user);
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
     ObjectMapper  mapper=new ObjectMapper();
     response.setContentType("application/json;charset=utf-8");
      mapper.writeValue(response.getOutputStream(),info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
