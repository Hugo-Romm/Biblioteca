/*package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.ag.pedidos.model.ItemPedido;
import com.ag.pedidos.model.Pedido;
import com.ag.pedidos.repository.Pedidos;
import com.ag.pedidos.util.jpa.Transactional;

public class StockService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pedidos pedidos;
	
	@Transactional
	public void bajarItensStock(Pedido pedido) {
		pedido = this.pedidos.porId(pedido.getId());
		
		for (ItemPrestamo item : pedido.getItens()) {
			item.getProducto().bajarStock(item.getCantidad());
		}
	}

	public void retornarItensStock(Pedido pedido) {
		pedido = this.pedidos.porId(pedido.getId());
		
		for (ItemPrestamo item : pedido.getItens()) {
			item.getProducto().adicionarStock(item.getCantidad());
		}
	}
	
}
*/