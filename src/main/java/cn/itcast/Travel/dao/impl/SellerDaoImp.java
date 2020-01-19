package cn.itcast.Travel.dao.impl;

import cn.itcast.Travel.dao.SellerDao;
import cn.itcast.Travel.domain.RouteImg;
import cn.itcast.Travel.domain.Seller;
import cn.itcast.Travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImp implements SellerDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.
            getDataSource());
    @Override
    public Seller findById(int sid) {
        String sql="select * from tab_seller where sid=";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
