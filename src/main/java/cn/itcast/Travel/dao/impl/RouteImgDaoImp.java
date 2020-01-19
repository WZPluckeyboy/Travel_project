package cn.itcast.Travel.dao.impl;

import cn.itcast.Travel.dao.RouteImgDao;
import cn.itcast.Travel.domain.Category;
import cn.itcast.Travel.domain.RouteImg;
import cn.itcast.Travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImp implements RouteImgDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.
            getDataSource());
    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql="select * from tab_route_img where rid=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }
}
