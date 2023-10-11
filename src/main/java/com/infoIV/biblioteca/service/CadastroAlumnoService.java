 package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Alumno;
import com.infoIV.biblioteca.repository.Alumnos;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroAlumnoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Alumnos alumnos;
	
	@Transactional
	public Alumno salvar(Alumno alumno) {
		Alumno alumnoExistente = alumnos.porNome(alumno.getNombre());
		
	if (alumnoExistente != null && !alumnoExistente.equals(alumno)){
			throw new NegocioException("Ya existe um alumno con nombre informado.");
		}
		
		
		return alumnos.guardar(alumno);
	}

	
}
