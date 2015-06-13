package com.example.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.model.Account;

@Repository
public class AccountDaoImpl extends AbstractDao<Account> implements AccountDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public String ehloDao(String greeting) {
		return "DAO = "+greeting;
	}
	
	@Override
	public List<Account> getAccountAndContact() {
		return entityManager.createQuery("FROM Account acct LEFT JOIN FETCH acct.contact").getResultList();
		
	}
	
}
