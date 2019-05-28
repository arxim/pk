package com.beans.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMConn {
	EntityManagerFactory emfactory;
	EntityManager entitymanager;
	
	public void EMConnConnect(){
      emfactory = Persistence.createEntityManagerFactory( "pkauto" );
      entitymanager = emfactory.createEntityManager( );		
	}
	public void EMConnClose(){
		this.entitymanager.close();
		this.emfactory.close();
	}
	public void save(Object obj){
		this.entitymanager.getTransaction().begin();
		this.entitymanager.persist(obj);
		this.entitymanager.getTransaction().commit();
	}
}
