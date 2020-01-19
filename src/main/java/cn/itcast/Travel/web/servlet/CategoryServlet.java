package cn.itcast.Travel.web.servlet;
import cn.itcast.Travel.domain.Category;
import cn.itcast.Travel.service.CategoryService;
import cn.itcast.Travel.service.impl.CategoryServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet  {
    private CategoryService categoryService=new CategoryServiceImp();
    //查询所有
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.查询所有
        List<Category> list =categoryService.findAll();
        //2.序列化成Json数据
        writeVal(list,response);
    }
}
