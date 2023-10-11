 package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Ciudad;
import com.infoIV.biblioteca.repository.Ciudads;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroCiudadService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Ciudads ciudads;
	
	@Transactional
	public Ciudad salvar(Ciudad ciudad) {
		Ciudad ciudadExistente = ciudads.porNome(ciudad.getDescri());
		
	if (ciudadExistente != null && !ciudadExistente.equals(ciudad)){
			throw new NegocioException("Ya existe um ciudad con nombre informado.");
		}
		
		
		return ciudads.guardar(ciudad);
	}

	
}
