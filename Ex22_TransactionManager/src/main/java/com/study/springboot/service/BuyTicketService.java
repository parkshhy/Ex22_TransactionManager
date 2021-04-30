package com.study.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.study.springboot.dao.ITransaction1Dao;
import com.study.springboot.dao.ITransaction2Dao;

//@Service 어노테이션을 지정하면 이 클래스를 빈으로 사용하겠다는 의미이다.
@Service
public class BuyTicketService implements IBuyTicketService{

	//Transaction1Dao 클래스의 객체를 자동 주입받아 변수를 만든다.
	@Autowired
	ITransaction1Dao transaction1;
	
	//Transaction2Dao 클래스의 객체를 자동 주입 받아 변수를 만든다.
	@Autowired
	ITransaction2Dao transaction2;
	
	
	@Autowired
	PlatformTransactionManager transactionManager;
	@Autowired
	TransactionDefinition definition;
	
	@Override
	public int buy(String consumerId,int amount,String error) {
	
		TransactionStatus status = transactionManager.getTransaction
				(definition);
		try {
			transaction1.pay(consumerId,amount);
			
			if(error.equals("1")) {int n = 10 / 0 ;}
			
			transaction2.pay(consumerId,amount);
			
			transactionManager.commit(status);
			
			return 1;
		
		}catch(Exception e){
			System.out.println("[PlatformTransactionManager] Rollback");
			transactionManager.rollback(status);
			return 0;
		}
	}
}
