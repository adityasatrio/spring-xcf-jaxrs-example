package com.example.service;

import java.util.List;

import com.example.model.Account;

public interface BusinessService {
	
	String ehloFromDao(String greeting);

	Account getAccountbyId(String id);

	List<Account> getAccounts() throws Exception;

	void create(Account account) throws Exception;
	

}
