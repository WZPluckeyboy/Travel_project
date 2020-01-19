package cn.itcast.Travel.dao.impl;

import cn.itcast.Travel.dao.UserDao;
import cn.itcast.Travel.domain.User;
import cn.itcast.Travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private  JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUsername(String username) {
        User user=null;
        try {
            String sql = "select * from tab_user where username=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){
            e.printStackTrace();
        }
      return user;
    }

    @Override
    public void save(User user) {
        String sql ="insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)"+
                "values(?,?,?,?,?,?,?,?,?)";
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),
                user.getBirthday(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),
                user.getStatus(),
                user.getCode());
    }
//给根据激活码查询用户状态
    @Override
    public User findByCode(String code) {
        User user=null;
       try {
           String sql ="select * from tab_user where code=?";
        user= template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
       }catch (Exception e){
           e.printStackTrace();
       }
       return user;
    }

    @Override
    public void updateStatus(User user) {
    String sql="update tab_user set status='Y' where uid= ?";
    template.update(sql,user.getUid());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user=null;
        try {
            String sql = "select * from tab_user where username=? and password=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
