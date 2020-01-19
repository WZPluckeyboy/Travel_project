package cn.itcast.Travel.web.servlet;

import cn.itcast.Travel.domain.PageBean;
import cn.itcast.Travel.domain.Route;
import cn.itcast.Travel.domain.User;
import cn.itcast.Travel.service.FavoriteService;
import cn.itcast.Travel.service.RoutService;
import cn.itcast.Travel.service.impl.FavoriteServiceImp;
import cn.itcast.Travel.service.impl.RoutServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/Rout/*")
//分页查询
public class RoutServlet extends BaseServlet {
    private RoutService routService=new RoutServiceImp();
    private FavoriteService favoriteService=new FavoriteServiceImp();
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //1.接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String  rname=request.getParameter("rname");
        //2.处理参数
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0&&!"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;
        if (currentPageStr != null &&currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage=1;
        }
       int pageSize=0;//每页显示数
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
           pageSize=1;
        }
        //3.调用service查询pageBean对象
       PageBean<Route> pb=routService.pageQuery(cid,currentPage,pageSize,rname);
        //4.将pageBean序列化为Json,返回
        writeVal(pb,response);
    }

    // 根据id查询一个旅游线路详情信息
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受id
        String rid=request.getParameter("rid");
        //2.调用servic
        Route route=routService.findOne(rid);
        //3.写回客户端
        writeVal(route,response);
    }
    //判断当前用户是否收藏该线路
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取id
         String rid=request.getParameter("rid");
         //2.获取登录用户名
       User user= (User) request.getSession().getAttribute("user");
       int uid;
       if(user==null){
          uid=0;
       }else{
           uid=user.getUid();
       }
       //3.查询是否收藏
      Boolean flag=  favoriteService.isFavorite(rid,uid);
       //4.写回客户端
        writeVal(flag,response);
    }
    //添加收藏
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     //1.获取线路rid
        String rid=request.getParameter("rid");
        //2.获取登录用户名
        User user= (User) request.getSession().getAttribute("user");
        int uid;
        if(user==null){
           return;
        }else{
            uid=user.getUid();
        }
        //3.调用添加
        favoriteService.add( rid, uid);
    }
}
