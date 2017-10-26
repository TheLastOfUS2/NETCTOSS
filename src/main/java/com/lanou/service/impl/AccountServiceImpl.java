package com.lanou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanou.bean.Account;
import com.lanou.mapper.AccountMapper;
import com.lanou.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dllo on 2017/10/23.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    public PageInfo<Account> queryCostByPage(Integer pageNo,Integer pageSize){
        //1.判断参数的合法性
        pageNo = pageNo ==null ? 1 : pageNo;
        pageSize = pageSize ==null ? 5 : pageSize;

        PageHelper.startPage(pageNo,pageSize);

        //获取全部学员
        List<Account> accountList = accountMapper.selectAccount();

        //使用PageInfo对结果进行包装
        PageInfo<Account> pageInfo =new PageInfo<Account>(accountList);
        return pageInfo;
    }

    public void addAccount(Account account) {
        accountMapper.insert(account);
    }

    public void deleteAccount(Integer id) {
        accountMapper.deleteByPrimaryKey(id);
    }

    public void updateAccount(Account account) {
        accountMapper.updateByPrimaryKeySelective(account);
    }

    public Account findById(Integer id) {
        Account account = accountMapper.selectByPrimaryKey(id);
        return account;
    }

    public Account findByIdcard(String rIdcard) {
        List<Account> byIdcard = accountMapper.findByIdcard(rIdcard);
        Account account = byIdcard.get(0);
        return account;
    }

    public List<Account> highSelect(String idcard, String name, String loginName, String status) {
        List<Account> accounts = highSelect(idcard, name, loginName, status);
        return accounts;
    }

    public PageInfo<Account> queryAccountByCondition(Integer pageNo, Integer pageSize, Account account) {
        //1.判断参数的合法性
        pageNo = pageNo ==null ? 1 : pageNo;
        pageSize = pageSize ==null ? 5 : pageSize;

        PageHelper.startPage(pageNo,pageSize);

        //获取全部学员
        List<Account> accountList = accountMapper.findByCondition(account);

        //使用PageInfo对结果进行包装
        PageInfo<Account> pageInfo =new PageInfo<Account>(accountList);
        return pageInfo;
    }
}
