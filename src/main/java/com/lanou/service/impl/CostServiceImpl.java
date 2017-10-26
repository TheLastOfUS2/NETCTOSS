package com.lanou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanou.bean.Cost;
import com.lanou.mapper.CostMapper;
import com.lanou.service.CostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dllo on 2017/10/20.
 */
@Service
public class CostServiceImpl implements CostService {
    @Resource
    private CostMapper costMapper;

    public List<Cost> selectAllCost() {
        return costMapper.selectAllCost();
    }

    public Cost findById(Integer id) {
        return costMapper.selectByPrimaryKey(id);
    }

    public void addFee(Cost cost) {
        costMapper.insert(cost);
    }

    public boolean selectByName(String name) {
        List<Cost> costs = costMapper.selectByName(name);
        return costs.size()>0;
    }

    public void update(Cost cost) {
        costMapper.updateByPrimaryKey(cost);
    }

    public void delete(Integer id) {
        costMapper.deleteByPrimaryKey(id);
    }



    public PageInfo<Cost> queryCostByPage(Integer pageNo,Integer pageSize){
        //1.判断参数的合法性
        pageNo = pageNo ==null ? 1 : pageNo;
        pageSize = pageSize ==null ? 5 : pageSize;

        PageHelper.startPage(pageNo,pageSize);

        //获取全部学员
        List<Cost> costList = costMapper.selectAllCost();

        //使用PageInfo对结果进行包装
        PageInfo<Cost> pageInfo =new PageInfo<Cost>(costList);
        return pageInfo;
    }
}

