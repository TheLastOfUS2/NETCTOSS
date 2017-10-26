package com.lanou.controller;

import com.github.pagehelper.PageInfo;
import com.lanou.bean.Account;
import com.lanou.service.impl.AccountServiceImpl;
import com.lanou.util.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dllo on 2017/10/23.
 */
@Controller
public class AccountController {
    @Resource
    private AccountServiceImpl accountService;

    @RequestMapping(value = "/account_add")
    public String account_add(){
        return "account/account_add";
    }

    @RequestMapping(value = "/account_detail")
    public String account_detail(){
        return "account/account_detail";
    }

    @RequestMapping(value = "/account_list")
    public String account_list(){
        return "account/account_list";
    }

    @RequestMapping(value = "/account_modi")
    public String account_modi(){
        return "account/account_modi";
    }

    @ResponseBody
    @RequestMapping(value = "/getAccountPageInfo")
    public PageInfo<Account> getFeeByPage(@RequestParam("no") Integer pageNo,
                                   @RequestParam("size") Integer pageSize) {
        PageInfo<Account> pageInfo = accountService.queryCostByPage(pageNo, pageSize);
        System.out.println(pageInfo);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/addAccount")
    public AjaxResult addAccount(Account account
    ,@RequestParam("rIdcardNo")String rIdcardNo) throws ParseException {
        System.out.println(rIdcardNo);
        String idCardNo18 = String.valueOf(account.getIdcardNo());
        String date = idCardNo18.substring(6, 10) + "-" +
                idCardNo18.substring(10, 12) + "-" +
                idCardNo18.substring(12, 14);
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = f.parse(date);
        Account byIdcard = accountService.findByIdcard(rIdcardNo);
        Integer accountId = byIdcard.getAccountId();
        System.out.println("-----------------");
        System.out.println(accountId);
        account.setRecommenderId(accountId);
        account.setBirthdate(parse);
        account.setStatus("1");
        account.setCreateDate(new Date());
        accountService.addAccount(account);
        return new AjaxResult();
    }


    @ResponseBody
    @RequestMapping(value = "/updateAccount")
    public AjaxResult update(Account account){
        accountService.updateAccount(account);
        return new AjaxResult();
    }

    //显示账户详情
    //通过id查找账户,并把账户存进session里面
    @ResponseBody
    @RequestMapping(value = "/save")
    public AjaxResult saveAccountToSession(
            @RequestParam("accountId") Integer aid,
            HttpServletRequest request) {

        //得到session
        HttpSession session = request.getSession();

        //根据id查找账户
        Account account = accountService.findById(aid);

        session.setAttribute("account", account);

        return new AjaxResult(account);

    }

    //进入详情页面,显示账户信息
    @ResponseBody
    @RequestMapping(value = "/getdetail")
    public AjaxResult getAccount(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("account");

        System.out.println(account);

        return new AjaxResult(account);
    }

    //修改账户信息
    @ResponseBody
    @RequestMapping(value = "/modiAccount", method = RequestMethod.POST)
    public AjaxResult modiAccount(Account account) {
        System.out.println(account);
        accountService.updateAccount(account);
        return new AjaxResult(account);
    }


    //删除账务账户信息
    @ResponseBody
    @RequestMapping(value = "/delAccount")
    public AjaxResult delete(@RequestParam("accountId") Integer aid) {
        Account account = accountService.findById(aid);

        account.setCloseDate(new Date());
        account.setStatus("2");

        accountService.updateAccount(account);
        return new AjaxResult(account);

    }


    //开启账户
    @ResponseBody
    @RequestMapping(value = "/open")
    public AjaxResult open(@RequestParam("accountId") Integer aid) {
        Account account = accountService.findById(aid);

        account.setPauseDate(null);

        account.setStatus("1");

        System.out.println(account);
        accountService.updateAccount(account);

        return new AjaxResult(account);
    }


    //暂停账户
    @ResponseBody
    @RequestMapping(value = "/pause")
    public AjaxResult pause(@RequestParam("accountId") Integer aid) {
        Account account = accountService.findById(aid);

        account.setPauseDate(new Date());
        account.setStatus("0");

        accountService.updateAccount(account);
        return new AjaxResult(account);
    }

    //模糊查询
    @ResponseBody
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public PageInfo<Account> fuzzyquery(@RequestParam("no") Integer pageNo,
                                        @RequestParam("size") Integer pageSize,
                                        @RequestParam("idcardNo") String idCard,
                                        @RequestParam("realname") String name,
                                        @RequestParam("loginName") String loginName,
                                        @RequestParam("status") String status) {
        if (idCard.equals("")) {
            idCard = null;
        }
        if (loginName.equals("")) {
            loginName = null;
        }
        if (name.equals("")) {
            name = null;
        }
        if (status.equals("")){
            status = null;
        }
        Account account = new Account();
        account.setIdcardNo(idCard);
        account.setLoginName(loginName);
        account.setRealName(name);
        account.setStatus(status);
        System.out.println(account);
        PageInfo<Account> pageInfo = accountService.queryAccountByCondition(pageNo, pageSize, account);

        return pageInfo;
    }

}
