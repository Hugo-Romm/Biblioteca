/*package com.infoIV.biblioteca.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.service.CancelamientoPrestamoService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CancelamientoPrestamoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CancelamientoPrestamoService cancelamientoPrestamoService;
	
	@Inject
	private Event<PrestamoAlteradoEvent> prestamoAlteradoEvent;
	
	@Inject
	@PrestamoEdicion
	private Prestamo prestamo;
	
	public void cancelarPrestamo() {
		this.prestamo = this.cancelamientoPrestamoService.cancelar(this.prestamo);
		this.prestamoAlteradoEvent.fire(new PrestamoAlteradoEvent(this.prestamo));
		
		FacesUtil.addInfoMessage("Prestamo cancelado!");
	}
	
}
*/