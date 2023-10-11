package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.repository.Lectors;
import com.infoIV.biblioteca.repository.filter.LectorFilter;
import com.infoIV.biblioteca.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaLectorsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Lectors lectors;
	
	private LectorFilter filtro;
	private List<Lector> lectorsFiltrados;
	
	private Lector lectorSelecionado;
	
	public PesquisaLectorsBean() {
		filtro = new LectorFilter();
	}
	
	public void excluir() {
		lectors.remover(lectorSelecionado);
		lectorsFiltrados.remove(lectorSelecionado);
		
		FacesUtil.addInfoMessage("Lector " + lectorSelecionado.getCodigo() 
				+ " excluído com exito.");
	}
	public void excluirTotal() {
		lectors.remover(lectorSelecionado);
		lectorsFiltrados.remove(lectorSelecionado);
		
		FacesUtil.addInfoMessage("Lector " + lectorSelecionado.getCodigo() 
				+ " excluído com exito.");
	}
	
	public void pesquisar() {
		lectorsFiltrados = lectors.filtrados(filtro);
	}
	
	public List<Lector> getLectorsFiltrados() {
		return lectorsFiltrados;
	}

	public LectorFilter getFiltro() {
		return filtro;
	}

	public Lector getLectorSelecionado() {
		return lectorSelecionado;
	}

	public void setLectorSelecionado(Lector lectorSelecionado) {
		this.lectorSelecionado = lectorSelecionado;
	}
	

	
}