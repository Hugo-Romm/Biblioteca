/*package com.infoIV.biblioteca.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.model.StatusPrestamo;
import com.infoIV.biblioteca.repository.Prestamos3;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroPrestamoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Prestamos3 prestamos;
	
	@Transactional
	public Prestamo salvar(Prestamo prestamo) {
		if (prestamo.isNuevo()) {
			prestamo.setFechaCreacion(new Date());
			prestamo.setStatus(StatusPrestamo.PRESUPUESTO);
		}
		
		prestamo.recalcularValorTotal();
		
		if (prestamo.isNoAlterable()) {
			throw new NegocioException("Prestamo no puede ser alterado al status "
					+ prestamo.getStatus().getDescricao() + ".");
		}
		
		if (prestamo.getItens().isEmpty()) {
			throw new NegocioException("El prestamo debe tener al menos un item.");
		}
		
		if (prestamo.isValorTotalNegativo()) {
			throw new NegocioException("Valor total del  prestamo no  puede ser negativo.");
		}
		
		prestamo = this.prestamos.guardar(prestamo);
		return prestamo;
	}
	
}
*/