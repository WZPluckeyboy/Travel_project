package cn.itcast.Travel.dao.impl;

import cn.itcast.Travel.dao.FavoriteDao;
import cn.itcast.Travel.domain.Favorite;
import cn.itcast.Travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImp implements FavoriteDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite=null;
        try {
            String sql = "select * from tab_favorite where rid=? and uid=?";
           favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public int findCount(int rid) {
        String sql="select Count(*) from tab_favorite where rid=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void add(int rid, int uid) {
        String sql="insert into tab_favorite value(?,?,？)";
        jdbcTemplate.update(sql,rid,new Date(),uid);
    }
}
