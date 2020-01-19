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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
         ObjectMapper mapper=new ObjectMapper();
         String json= mapper.writeValueAsString(info);
         response.setContentType("application/json;charset=utf-8");
         response.getWriter().write(json);
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
      UserService service=new UserServiceImpl();
      boolean flag= service.regist(user);
        if(flag){
            info.setFlag(true);

        }else{
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }
        ObjectMapper mapper=new ObjectMapper();
       String json= mapper.writeValueAsString(info);
       response.setContentType("application/json;charset=utf-8");
       response.getWriter().write(json);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
