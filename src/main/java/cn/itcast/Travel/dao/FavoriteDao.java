package cn.itcast.Travel.dao;

import cn.itcast.Travel.domain.Favorite;

public interface FavoriteDao {
    //根据 rid and uid查询收藏信息
    public Favorite findByRidAndUid(int rid, int uid);

   public int findCount(int rid);

   public void add(int rid, int uid);
}
