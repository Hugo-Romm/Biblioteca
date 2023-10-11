 package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Docente;
import com.infoIV.biblioteca.repository.Docentes;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroDocenteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Docentes docentes;
	
	@Transactional
	public Docente salvar(Docente docente) {
		Docente docenteExistente = docentes.porNome(docente.getNombre());
		
	if (docenteExistente != null && !docenteExistente.equals(docente)){
			throw new NegocioException("Ya existe um docente con nombre informado.");
		}
		
		
		return docentes.guardar(docente);
	}

	
}
