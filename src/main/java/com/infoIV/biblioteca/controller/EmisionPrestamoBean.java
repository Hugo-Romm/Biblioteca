/*package com.infoIV.biblioteca.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.ag.pedidos.model.Pedido;
import com.ag.pedidos.service.EmisionPedidoService;
import com.ag.pedidos.util.jsf.FacesUtil;

@Named
@RequestScoped
public class EmisionPrestamoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmisionPrestamoService emisionPrestamoService;
	
	@Inject
	@PrestamoEdicion
	private Pedido pedido;
	
	@Inject
	private Event<PrestamoAlteradoEvent> prestamoAlteradoEvent;
	
	public void emitirPedido() {
		System.out.println("emitir pedido");
		this.pedido.removerItemVazio();
		
		try {
			this.pedido = this.emisionPrestamoService.emitir(this.pedido);
			this.prestamoAlteradoEvent.fire(new PrestamoAlteradoEvent(this.pedido));
			
			FacesUtil.addInfoMessage("Pedido emitido com exito!");
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}
	
}
*/