package cn.itcast.Travel.dao;

import cn.itcast.Travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    //根据rid查询图片
    public List<RouteImg> findByRid(int rid);
}
