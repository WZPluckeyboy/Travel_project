package cn.itcast.Travel.dao.impl;

import cn.itcast.Travel.dao.CategoryDao;
import cn.itcast.Travel.domain.Category;
import cn.itcast.Travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImp implements CategoryDao {
   private JdbcTemplate  jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql="select * from  tab_category";
      return   jdbcTemplate.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }
}
