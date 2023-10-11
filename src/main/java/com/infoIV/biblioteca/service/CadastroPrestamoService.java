 package com.infoIV.biblioteca.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.Prestamos;
import com.infoIV.biblioteca.repository.filter.PrestamoFilter;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroPrestamoService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	@Inject
    private Prestamos prestamos;
	private PrestamoFilter filtro = new PrestamoFilter();
	private List<Prestamo> prestamosFiltrados;
	
	@Transactional
	public Prestamo verificar(Prestamo prestamo){
		System.out.println("verificar");
		prestamosFiltrados = prestamos.filtradosRestriccion(prestamo);
		for (Prestamo prestamoss : prestamosFiltrados) {
			System.out.println("libro = "+prestamoss.getLibro().getCodigo());
			System.out.println("Ejemplar = "+ prestamoss.getNroeje());
			System.out.println("Fecha Recuperacion = "+ prestamoss.getFecrec());
		}

		
	if (this.prestamosFiltrados.isEmpty() ){
		       System.out.println("Si está vacio");
		        
		       return prestamos.guardar(prestamo);
		}else {
			 System.out.println("prestamo fecha"+prestamo.getFecrec());
				throw new NegocioException("Ya existe un prestamo con libro del mismo ejemplar ingresado.");

		}
	
	}
	@Transactional
	public Prestamo salvar(Prestamo prestamo) {
		
		System.out.println("guardado");
		 
	
		return prestamos.guardar(prestamo);

	}
	public List<Prestamo> getPrestamosFiltrados() {
		return prestamosFiltrados;
	}

	public PrestamoFilter getFiltro() {
		return filtro;
	}
	
}
