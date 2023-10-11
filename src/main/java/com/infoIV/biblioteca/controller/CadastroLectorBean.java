package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.repository.Lectors;
import com.infoIV.biblioteca.repository.filter.EditorFilter;
import com.infoIV.biblioteca.repository.filter.LectorFilter;
import com.infoIV.biblioteca.service.CadastroLectorService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
@ManagedBean
@Named
@ViewScoped
public class CadastroLectorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private CadastroLectorService cadastroLectorService;
	
	private Lector lector;	
	
	@Inject
	private Lectors lectors;

	
    private LectorFilter filtro;
    
	
	private List<Lector> lectorsFiltrados;
	
	private Lector lectorSelecionado;
		
	
	
	

	public CadastroLectorBean() {
		limpar();
	}

	public void inicializar() {
			if (FacesUtil.isNotPostback()) {
			
		}
	}


	private void limpar() {
		lector = new Lector();
		filtro = new LectorFilter();		
		
	}

	public void salvar() {
		this.lector = (Lector) cadastroLectorService.salvar(this.lector);
		limpar();

		FacesUtil.addInfoMessage("Lector se agrego corectamente!");
	}

	public Lector getLector() {
		return lector;
	}

	public void setLectore(Lector lector) {
		this.lector = lector;
		
	}

	

	
	
	public boolean isEditando() {
		return this.lector.getCodigo() != null;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
		
	}

	public LectorFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(LectorFilter filtro) {
		this.filtro = filtro;
	}

	public List<Lector> getLectorsFiltrados() {
		return lectorsFiltrados;
	}

	public void setLectorsFiltrados(List<Lector> lectorsFiltrados) {
		this.lectorsFiltrados = lectorsFiltrados;
	}

	public Lector getLectorSelecionado() {
		return lectorSelecionado;
	}

	public void setLectorSelecionado(Lector lectorSelecionado) {
		this.lectorSelecionado = lectorSelecionado;
	}
	
	public void excluir() {
		lectors.remover(lectorSelecionado);
		lectorsFiltrados.remove(lectorSelecionado);
		
		FacesUtil.addInfoMessage("Lector " + lectorSelecionado.getCodigo() 
				+ " excluído com exito.");
	}
	
	
	public void pesquisar() {
		lectorsFiltrados = (filtro.getNombre().isEmpty())?lectors.lista():lectors.filtrados(filtro);
	}
	
	public int getCantidad() {
		return (lectorsFiltrados != null)?lectorsFiltrados.size():0;
	}

}