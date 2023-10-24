 package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Carro;
import com.infoIV.biblioteca.repository.Carros;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroCarroService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Carros carros;
	
	@Transactional
	public Carro salvar(Carro carro) {
//		Carro carroExistente = carros.porNome(carro.getDescri());
		
//	if (carroExistente != null && !carroExistente.equals(carro)){
//			throw new NegocioException("Ya existe um carro con descri informado.");
//		}
		
		
		return carros.guardar(carro);
	}

	
}
