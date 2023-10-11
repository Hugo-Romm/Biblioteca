/*package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.model.StatusPrestamo;
import com.infoIV.biblioteca.repository.Prestamos3;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CancelamientoPrestamoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Prestamos3 prestamos;
	
	@Inject
	private StockService stockService;
	
	@Transactional
	public Prestamo cancelar(Prestamo prestamo) {
		prestamo = this.prestamos.porNumero(prestamo.getNumero());
		
		if (prestamo.isNoCancelable()) {
			throw new NegocioException("Prestamo no puede ser cancelado al status "
					+ prestamo.getStatus().getDescricao() + ".");
		}
		
		if (prestamo.isDevuelto()) {
			this.stockService.retornarItensStock(prestamo);
		}
		
		prestamo.setStatus(StatusPrestamo.ELIMINADO);
		
		prestamo = this.prestamos.guardar(prestamo);
		
		return prestamo;
	}

}
*/