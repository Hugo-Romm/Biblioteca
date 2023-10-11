package com.infoIV.biblioteca.security;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.infoIV.biblioteca.model.Config;

@Named
@RequestScoped
public class confifi {
String nombre;
	public String getNomeEmpresa() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("PedidoPU");
		EntityManager em = emf.createEntityManager();
		List<Config> config = em.createQuery("from Config ", Config.class)
				.getResultList();
		return this.nombre= config.get(0).getOrganom();
	}
	

	}
	
