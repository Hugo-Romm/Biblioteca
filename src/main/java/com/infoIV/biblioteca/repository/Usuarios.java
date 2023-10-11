package com.infoIV.biblioteca.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.infoIV.biblioteca.model.Usuario;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Usuario porId(Long id) {
		return this.manager.find(Usuario.class, id);
	}
	
	public List<Usuario> funcionarios() {
		// TODO filtrar apenas vendedores (por um grupo específico)
		return this.manager.createQuery("from Usuario", Usuario.class)
				.getResultList();
	}
	
	public List<Usuario> porNome(String nome) {
		return this.manager.createQuery("from Usuario " +
				"where upper(nome) like :nome", Usuario.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
	public Usuario porEmail(String email) {
		Usuario usuario = null;
		
		try {
			usuario = this.manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
				.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// sin usuário encontrado con  e-mail informado
		}
		
		return usuario;
	}
	
}