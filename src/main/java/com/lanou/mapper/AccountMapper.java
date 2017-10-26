package com.lanou.mapper;

import com.lanou.bean.Account;

import java.util.List;

public interface AccountMapper {
    int deleteByPrimaryKey(Integer accountId);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer accountId);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    List<Account> selectAccount();

    List<Account> findByIdcard(String rIdcard);

    List<Account> highSelect
            (String idcard,String name,String loginName,String status);

    List<Account> findByCondition(Account account);

}