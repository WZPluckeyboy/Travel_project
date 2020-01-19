package cn.itcast.Travel.dao;

import cn.itcast.Travel.domain.Category;

import java.util.List;
import java.util.Locale;

public interface CategoryDao {
    public List<Category> findAll();
}
