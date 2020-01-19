package cn.itcast.Travel.service.impl;
import cn.itcast.Travel.dao.CategoryDao;
import cn.itcast.Travel.dao.impl.CategoryDaoImp;
import cn.itcast.Travel.domain.Category;
import cn.itcast.Travel.service.CategoryService;
import cn.itcast.Travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
public class CategoryServiceImp implements CategoryService{
private CategoryDao categoryDao=new CategoryDaoImp();
    @Override
    public List<Category> findAll() {
        //1.从redis缓存中查询
        Jedis jedis=JedisUtil.getJedis();
       // Set<String> set= jedis.zrange("category",0,-1);
        Set<Tuple> set=jedis.zrangeWithScores("category",0,-1);
        List<Category> list=null;
        //2.判断是否为空
        if(set==null||set.size()==0){
         list=  categoryDao.findAll();
         for(int i=0;i<list.size();i++) {
             jedis.zadd("category",list.get(i).getCid(),list.get(i).getCname());
         }
        }else{
            System.out.println("从redis中进行查询");
            list=new ArrayList<Category>();
            for(Tuple c:set){
            Category category= new Category();
            category.setCname(c.getElement());
            category.setCid((int)c.getScore());
           list.add(category);
            }
        }
        return list;
    }
}
