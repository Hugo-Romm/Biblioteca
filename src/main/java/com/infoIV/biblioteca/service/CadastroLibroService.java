 package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.repository.Libros;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroLibroService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Libros libros;
	
	@Transactional
	public Libro salvar(Libro libro) {
//		Libro libroExistente = libros.porNome(libro.getDescri());
		
//	if (libroExistente != null && !libroExistente.equals(libro)){
//			throw new NegocioException("Ya existe um libro con descri informado.");
//		}
		
		
		return libros.guardar(libro);
	}

	
}
