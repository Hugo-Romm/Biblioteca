 package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Config;
import com.infoIV.biblioteca.repository.Configs;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroConfigService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Configs configs;
	
/*	@Transactional
	public void salvar(Configs pedido) {
		pedido = this.pedidos.porId(pedido.getId());
		
		for (ItemPrestamo item : pedido.getItens()) {
			item.getProducto().bajarStock(item.getCantidad());
		}
	}*/
	@Transactional
	public Config salvar(Config config) {
		Config configExistente = configs.porNome(config.getOrganom());
		
/*	if (configExistente != null && !configExistente.equals(config)){
			throw new NegocioException("Ya existe um config con nombre informado.");
		}*/
		
		
		return configs.guardar(config);
	}

	
}
