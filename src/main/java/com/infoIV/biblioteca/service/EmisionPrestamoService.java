/*package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.ag.pedidos.model.Pedido;
import com.ag.pedidos.model.StatusPedido;
import com.ag.pedidos.repository.Pedidos;
import com.ag.pedidos.util.jpa.Transactional;

public class EmisionPrestamoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	@Inject
	private StockService stockService;
	
	@Inject
	private Pedidos pedidos;
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		pedido = this.cadastroPedidoService.salvar(pedido);
		
		if (pedido.isNaoEmissivel()) {
			throw new NegocioException("Pedido no puede ser emitido con status "
					+ pedido.getStatus().getDescricao() + ".");
		}
		
		this.stockService.bajarItensStock(pedido);
		
		pedido.setStatus(StatusPrestamo.EMITIDO);
		
		pedido = this.pedidos.guardar(pedido);
		
		return pedido;
	}
	
}
*/