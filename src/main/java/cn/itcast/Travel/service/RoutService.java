package cn.itcast.Travel.service;

import cn.itcast.Travel.domain.PageBean;
import cn.itcast.Travel.domain.Route;

//线路service
public interface RoutService {
    public PageBean<Route> pageQuery(int cid,int current,int pageSize,String rname);

      public   Route findOne(String rid);
}
