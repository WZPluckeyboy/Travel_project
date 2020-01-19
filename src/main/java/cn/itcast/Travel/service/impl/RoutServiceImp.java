package cn.itcast.Travel.service.impl;
import cn.itcast.Travel.dao.FavoriteDao;
import cn.itcast.Travel.dao.RoutDao;
import cn.itcast.Travel.dao.RouteImgDao;
import cn.itcast.Travel.dao.SellerDao;
import cn.itcast.Travel.dao.impl.FavoriteDaoImp;
import cn.itcast.Travel.dao.impl.RoutDaoImp;
import cn.itcast.Travel.dao.impl.RouteImgDaoImp;
import cn.itcast.Travel.dao.impl.SellerDaoImp;
import cn.itcast.Travel.domain.PageBean;
import cn.itcast.Travel.domain.Route;
import cn.itcast.Travel.domain.RouteImg;
import cn.itcast.Travel.domain.Seller;
import cn.itcast.Travel.service.RoutService;

import java.util.List;

public class RoutServiceImp implements RoutService {
    private RoutDao routDao=new RoutDaoImp();
    private RouteImgDao routeImgDao=new RouteImgDaoImp();
    private SellerDao sellerDao=new SellerDaoImp();
    private FavoriteDao favorite=new FavoriteDaoImp();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        PageBean<Route> pb=new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);//设置每页显示条数
        pb.setTotalCount(routDao.findTotaCount(cid,rname));
        int start=(currentPage-1)*pageSize;
        pb.setList(routDao.findBypage(cid,start,pageSize,rname));
        int totalPages=routDao.findTotaCount(cid,rname)%pageSize==0?(routDao.findTotaCount(cid,rname)/pageSize):
                (routDao.findTotaCount(cid,rname)/pageSize)+1;
        pb.setTotalPages(totalPages);
        return pb;
    }
//根据id查询
    @Override
    public Route findOne(String rid) {
        Route route= routDao.findOne(Integer.parseInt(rid));
        List<RouteImg> list= routeImgDao.findByRid(Integer.parseInt(rid));
        route.setRouteImgList(list);
        Seller seller=sellerDao.findById(route.getSid());
        route.setSeller(seller);
        //查询收藏次数
         int count=favorite.findCount(route.getRid());
         route.setCount(count);
        return  route;
    }

}
