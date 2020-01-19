package cn.itcast.Travel.service.impl;

import cn.itcast.Travel.dao.UserDao;
import cn.itcast.Travel.dao.impl.UserDaoImpl;
import cn.itcast.Travel.domain.User;
import cn.itcast.Travel.service.UserService;
import cn.itcast.Travel.util.MailUtils;
import cn.itcast.Travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
private  UserDao userDao=new UserDaoImpl();

    @Override
    public boolean regist(User user) {
      User u= userDao.findByUsername(user.getUsername());
      if(u!=null){
          return false;
      }else{
          //保存用户信息，设置激活码以及设置激活状态
          user.setStatus("N");
          user.setCode(UuidUtil.getUuid());
          userDao.save(user);
          String content="<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
          MailUtils.sendMail(user.getEmail(),content,"激活邮件");
      }
     return true;
    }
//激活
    @Override
    public boolean active(String code) {
       //1.根据激活码查询用户对象
        User user=userDao.findByCode(code);
        if(user!=null){
            //2.修改激活码状态
            userDao.updateStatus(user);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public User login(User user){
       return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
