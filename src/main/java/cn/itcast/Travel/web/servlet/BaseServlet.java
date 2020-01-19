package cn.itcast.Travel.web.servlet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String url=req.getRequestURI();
      String methodName= url.substring(url.lastIndexOf('/')+1);
        Method meth=null;
        try {
         meth=   this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
        // meth.setAccessible(true);
         meth.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    //直接将出入的对象序列化为json，并且传入客户端
    public void writeVal(Object obj,HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),obj);
    }
    public void writevalASString(Object obj,HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        String json= objectMapper.writeValueAsString(obj);
        response.getWriter().write(json);
    }
}
