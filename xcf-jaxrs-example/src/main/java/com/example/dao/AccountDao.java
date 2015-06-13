package com.example.dao;

import java.util.List;

import com.example.model.Account;

public interface AccountDao extends BaseDao<Account> {
	
	String ehloDao(String greeting);

	List<Account> getAccountAndContact();

	
	

}
