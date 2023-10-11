/*package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ag.pedidos.model.Pedido;
import com.ag.pedidos.model.StatusPedido;
import com.ag.pedidos.repository.Pedidos;
import com.ag.pedidos.repository.filter.PedidoFilter;

@Named
@ViewScoped
public class ConsultarPrestamoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Prestamos3 prestamos;
	
	private PrestamoFilter filtro;
	private List<Pedido> pedidosFiltrados;
	
	public ConsultarPrestamoBean() {
		filtro = new PrestamoFilter();
		pedidosFiltrados = new ArrayList<>();
	}

	public void consultar() {
		pedidosFiltrados = prestamos.filtrados(filtro);
	}
	
	public StatusPrestamo[] getStatuses() {
		return StatusPrestamo.values();
	}
	
	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}

	public PrestamoFilter getFiltro() {
		return filtro;
	}
	
}*/