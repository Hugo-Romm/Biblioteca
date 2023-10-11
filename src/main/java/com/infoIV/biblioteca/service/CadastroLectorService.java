 package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.repository.Lectors;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroLectorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Lectors lectors;
	
	@Transactional
	public Lector salvar(Lector lector) {
		Lector lectorExistente = lectors.porNome(lector.getNombre());
		
	if (lectorExistente != null && !lectorExistente.equals(lector)){
			throw new NegocioException("Ya existe um lector con nombre informado.");
		}
		
		
		return lectors.guardar(lector);
	}

	
}
