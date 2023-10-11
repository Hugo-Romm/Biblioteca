/*package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.Lectors;
import com.infoIV.biblioteca.repository.Libros;
import com.infoIV.biblioteca.service.CadastroPrestamoService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroPrestamoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private Lectors lectors;
	
	@Inject
	private Libros libros;
	
	@Inject
	private CadastroPrestamoService cadastroPrestamoService;
	
	private String sku;
	
	@Produces
	@PrestamoEdicion
	private Prestamo prestamo;
	
	private Libro libroLinhaEditavel;
	
	public CadastroPrestamoBean() {
		limpar();
	}
	
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
					
			this.prestamo.adicionarItemVazio();
			
			this.recalcularPrestamo();
		}
	}
	
	private void limpar() {
		prestamo = new Prestamo();
		prestamo.setDireccionEntrega(new DireccionEntrega());
	}
	
	public void prestamoAlterado(@Observes PrestamoAlteradoEvent event) {
		this.prestamo = event.getPrestamo();
	}
	
	public void salvar() {
		this.prestamo.removerItemVazio();
		
		try {
			this.prestamo = this.cadastroPrestamoService.salvar(this.prestamo);
		
			FacesUtil.addInfoMessage("Prestamo guardado con exito!");
		} finally {
			this.prestamo.adicionarItemVazio();
		}
	}
	
	public void recalcularPrestamo() {
		if (this.prestamo != null) {
			this.prestamo.recalcularValorTotal();
		}
	}
	
	public void carregarLibroPorSku() {
		if (StringUtils.isNotEmpty(this.sku)) {
			this.libroLinhaEditavel = this.libros.porSku(this.sku);
			this.carregarLibroLinhaEditavel();
		}
	}
	
	public void carregarLibroLinhaEditavel() {
		ItemPrestamo item = this.prestamo.getItens().get(0);
		
		if (this.libroLinhaEditavel != null) {
			if (this.existeItemComLibro(this.libroLinhaEditavel)) {
				FacesUtil.addErrorMessage("Este prestamo ya posee un iten con el libro informado; altere la cantidad");
			} else {
				item.setLibro(this.libroLinhaEditavel);
				item.setValorUnitario(this.libroLinhaEditavel.getValorUnitario());
				
				this.prestamo.adicionarItemVazio();
				this.libroLinhaEditavel = null;
				this.sku = null;
				
				this.prestamo.recalcularValorTotal();
			}
		}
	}
	
	private boolean existeItemComLibro(Libro libro) {
		boolean existeItem = false;
		
		for (ItemPrestamo item : this.getPrestamo().getItens()) {
			if (libro.equals(item.getLibro())) {
				existeItem = true;
				break;
			}
		}
		
		return existeItem;
	}

	public List<Libro> completarLibro(String nombre) {
		return this.libros.porNombre(nombre);
	}
	
	public void actualizarCantidad(ItemPrestamo item, int linha) {
		if (item.getCantidad() < 1) {
			if (linha == 0) {
				item.setCantidad(1);
			} else {
				this.getPrestamo().getItens().remove(linha);
			}
		}
		
		this.prestamo.recalcularValorTotal();
	}
	
	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}
	
	public List<Lector> completarLector(String nombre) {
		return this.lectors.porNombreLista(nombre);
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}
	
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public boolean isEditando() {
		return this.prestamo.getId() != null;
	}

	public Libro getLibroLinhaEditavel() {
		return libroLinhaEditavel;
	}

	public void setLibroLinhaEditavel(Libro libroLinhaEditavel) {
		this.libroLinhaEditavel = libroLinhaEditavel;
	}

	@SKU
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

}*/