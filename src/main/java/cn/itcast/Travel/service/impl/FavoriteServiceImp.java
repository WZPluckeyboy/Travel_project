package cn.itcast.Travel.service.impl;

import cn.itcast.Travel.dao.FavoriteDao;
import cn.itcast.Travel.dao.impl.FavoriteDaoImp;
import cn.itcast.Travel.domain.Favorite;
import cn.itcast.Travel.service.FavoriteService;

public class FavoriteServiceImp implements FavoriteService {
    private FavoriteDao favoriteDao=new FavoriteDaoImp();
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite= favoriteDao.findByRidAndUid(Integer.parseInt(rid),uid);
        return favorite !=null;//如果对象有值表示收藏过，反之没有收藏
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
