package cn.itcast.Travel.dao;

import cn.itcast.Travel.domain.Seller;

public interface SellerDao {
    public Seller findById(int sid);
}
