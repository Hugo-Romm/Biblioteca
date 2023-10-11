 package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Imprenta;
import com.infoIV.biblioteca.repository.Imprentas;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroImprentaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Imprentas imprentas;
	
	@Transactional
	public Imprenta salvar(Imprenta imprenta) {
		Imprenta imprentaExistente = imprentas.porNome(imprenta.getRazonsocial());
		
	if (imprentaExistente != null && !imprentaExistente.equals(imprenta)){
			throw new NegocioException("Ya existe una imprenta con nombre informado.");
		}
		
		
		return imprentas.guardar(imprenta);
	}

	
}
