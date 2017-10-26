package com.lanou.service;

import com.github.pagehelper.PageInfo;
import com.lanou.bean.Cost;

import java.util.List;

/**
 * Created by dllo on 2017/10/20.
 */
public interface CostService {
    List<Cost> selectAllCost();

    Cost findById(Integer id);

    void addFee(Cost cost);

    boolean selectByName(String name);

    void update(Cost cost);

    void delete(Integer id);

    PageInfo<Cost> queryCostByPage(Integer pageNo,Integer pageSize);
}
