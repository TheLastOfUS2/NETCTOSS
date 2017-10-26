package com.lanou.controller;

import com.github.pagehelper.PageInfo;
import com.lanou.bean.Cost;
import com.lanou.service.impl.CostServiceImpl;
import com.lanou.util.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by dllo on 2017/10/20.
 */
@Controller
public class CostController {
    @Resource
    private CostServiceImpl costService;

    //资费页面,显示所有
    @RequestMapping(value = "/fee_list")
    public String toFeeList() {
        return "fee/fee_list";
    }

    @ResponseBody
    @RequestMapping(value = "/selectAll")
    public AjaxResult selectAll() {
        List<Cost> costs = costService.selectAllCost();
        return new AjaxResult(costs);
    }

    @RequestMapping(value = "/fee_detail/{id}")
    public String toFeeDetail(
            HttpServletRequest request,
            @PathVariable("id") Integer id) {
        request.getSession().setAttribute("costId", id);
        return "fee/fee_detail";
    }

    @ResponseBody
    @RequestMapping("/startFee")
    public AjaxResult startFee(
            @RequestParam("id") Integer id){
        System.out.println(id);
        System.out.println("-----------");
        Cost cost = costService.findById(id);
        System.out.println(cost);
        cost.setStatus("1");
        cost.setStartime(new Date());
        costService.update(cost);
        return new AjaxResult();
    }

    @ResponseBody
    @RequestMapping("/deleteFee")
    public AjaxResult deleteFee(
            @RequestParam("id") Integer id){
        costService.delete(id);
        return new AjaxResult(id);
    }

    @RequestMapping(value = "/fee_modi/{id}")
    public String tofeeModi(
            HttpServletRequest request,
            @PathVariable("id") Integer id) {
        request.getSession().setAttribute("costId", id);
        return "fee/fee_modi";
    }

    @ResponseBody
    @RequestMapping(value = "/findById")
    public AjaxResult findById(HttpServletRequest request) {
        Integer costId = (Integer) request.getSession().getAttribute("costId");
        Cost byId = costService.findById(costId);
        return new AjaxResult(byId);
    }

    @RequestMapping(value = "/fee_add")
    public String toFeeAdd() {
        return "fee/fee_add";
    }

    @ResponseBody
    @RequestMapping(value = "/updateFee")
    public AjaxResult update(Cost cost){
        System.out.println(cost);
        costService.update(cost);
        return new AjaxResult();
    }

    @ResponseBody
    @RequestMapping(value = "/addFee")
    public AjaxResult addFee(Cost cost) {
        String name = cost.getName();
        boolean b = costService.selectByName(name);
        System.out.println(b);
        if (!b) {
            cost.setCreatime(new Date());
            costService.addFee(cost);
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.setErrorCode(0);
            return ajaxResult;
        } else {
            AjaxResult ajaxResult1 = new AjaxResult();
            ajaxResult1.setErrorCode(5);
            return ajaxResult1;
        }

    }


    @ResponseBody
    @RequestMapping(value = "/getPageInfo")
    public AjaxResult getFeeByPage(@RequestParam("no") Integer pageNo,
                                       @RequestParam("size") Integer pageSize) {
        PageInfo<Cost> pageInfo = costService.queryCostByPage(pageNo, pageSize);
        System.out.println(pageInfo);
        return new AjaxResult(pageInfo);
    }



}
