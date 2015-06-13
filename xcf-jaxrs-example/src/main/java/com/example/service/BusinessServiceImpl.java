package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.AccountDao;
import com.example.model.Account;
import com.example.model.Contact;

@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	private AccountDao accountDao;

	@Override
	public String ehloFromDao(String greeting) {
		return accountDao.ehloDao(greeting);
	}

	@Override
	public Account getAccountbyId(String id) {
		
		Account existingAccount = accountDao.get(Long.valueOf(id));
		return new Account(existingAccount.getId(), existingAccount.getAccountNo(), existingAccount.getBalance(), existingAccount.getLastPaidOn());
	}

	@Override
	public List<Account> getAccounts() throws Exception {
		List<Account> accounts = accountDao.getAccountAndContact();
		List<Account> accountswithContact = new ArrayList<>();

		for (Account acct : accounts) {
			Account newAcct = new Account(acct.getAccountNo(),acct.getBalance(), acct.getLastPaidOn());
			newAcct.setId(acct.getId());
			
			if (acct.getContact() != null) {
				Contact contact = new Contact();
				contact.setId(acct.getContact().getId());
				contact.setFirstName(acct.getContact().getFirstName());
				contact.setLastName(acct.getContact().getLastName());
				contact.setMiddleInitial(acct.getContact().getMiddleInitial()); 
				contact.setEmail(acct.getContact().getEmail());
				
				newAcct.setContact(contact);
			}
			accountswithContact.add(newAcct);
		}

		return accountswithContact;
	}

	@Override
	public void create(Account account) throws Exception {
		accountDao.create(account);

	}
}
