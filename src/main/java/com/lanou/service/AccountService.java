package com.lanou.service;

import com.github.pagehelper.PageInfo;
import com.lanou.bean.Account;

import java.util.List;

/**
 * Created by dllo on 2017/10/23.
 */
public interface AccountService {

    PageInfo<Account> queryCostByPage(Integer pageNo, Integer pageSize);

    void addAccount(Account account);

    void deleteAccount(Integer id);

    void updateAccount(Account account);

    Account findById(Integer id);

    Account findByIdcard(String rIdcard);

    List<Account> highSelect
            (String idcard,String name,String loginName,String status);

    PageInfo<Account> queryAccountByCondition(Integer pageNo,Integer pageSize,Account account);
}
