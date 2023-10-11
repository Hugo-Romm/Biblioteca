 package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Medida;
import com.infoIV.biblioteca.repository.Medidas;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroMedidaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Medidas medidas;
	
	@Transactional
	public Medida salvar(Medida medida) {
		Medida medidaExistente = medidas.porNome(medida.getDescri());
		
	if (medidaExistente != null && !medidaExistente.equals(medida)){
			throw new NegocioException("Ya existe um medida con nombre informado.");
		}
		
		
		return medidas.guardar(medida);
	}

	
}
