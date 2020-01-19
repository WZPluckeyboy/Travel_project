package cn.itcast.Travel.dao;
import cn.itcast.Travel.domain.Route;
import java.util.List;
public interface RoutDao {
    public int findTotaCount(int cid,String rname);
    public List<Route> findBypage(int cid,int start,int pageSize,String rname);
    public Route findOne(int rid);
}
