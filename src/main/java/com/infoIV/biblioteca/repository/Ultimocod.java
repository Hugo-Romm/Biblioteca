package com.infoIV.biblioteca.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.infoIV.biblioteca.model.Lector;

public class Ultimocod implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Lector porMax(Long codigo) {
		try {
			return manager.createQuery("selec max(codigo) from Lector", Lector.class)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
		
	

}
	
	


	

