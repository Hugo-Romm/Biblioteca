package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Ciudad;
import com.infoIV.biblioteca.repository.Ciudads;
import com.infoIV.biblioteca.repository.filter.CiudadFilter;
import com.infoIV.biblioteca.service.CadastroCiudadService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
@ManagedBean
@Named
@ViewScoped
public class CadastroCiudadBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	

	@Inject
	private CadastroCiudadService cadastroCiudadService;

	private Ciudad ciudad;
	@Inject
	private Ciudads ciudads;
	private Ciudad ciudadSelecionado;

	private CiudadFilter filtro;
	

	private List<Ciudad> ciudadsFiltrados;

	

	public CadastroCiudadBean() {
		limpar();
	}

	public void inicializar() {
		/* System.out.println("Inicializando..."); */

		if (FacesUtil.isNotPostback()) {
			
		}
	}

	

	private void limpar() {
		ciudad = new Ciudad();
		filtro = new CiudadFilter();
		
	}


	public void salvar() {
		this.ciudad = (Ciudad) cadastroCiudadService.salvar(this.ciudad);
		limpar();
		FacesUtil.addInfoMessage("Ciudad se agregó correctamente!");
	}

	public Ciudad getCiudad() {
		return ciudad;
	}


	
	
	public boolean isEditando() {
		return this.ciudad.getCodigo() != null;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
		
	}
	
	public Ciudads getCiudads() {
		return ciudads;
	}

	public void setCiudads(Ciudads ciudads) {
		this.ciudads = ciudads;
	}

	public Ciudad getCiudadSelecionado() {
		return ciudadSelecionado;
	}

	public void setCiudadSelecionado(Ciudad ciudadSelecionado) {
		this.ciudadSelecionado = ciudadSelecionado;
	}

	public CiudadFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(CiudadFilter filtro) {
		this.filtro = filtro;
	}

	public List<Ciudad> getCiudadsFiltrados() {
		return ciudadsFiltrados;
	}

	public void excluir() {
		ciudads.remover(ciudadSelecionado);
		ciudadsFiltrados.remove(ciudadSelecionado);    		
		
		FacesUtil.addInfoMessage("Editor " + ciudadSelecionado.getCodigo()
				+ " eliminado con éxito.");
	}	
	

	public void pesquisar() {
		
		ciudadsFiltrados = (filtro.getDescri().isEmpty()) ?  ciudads.lista() : ciudads.filtrados(filtro);			

	}
	
	public int getCantidad() {
		int i = 0;
		if (ciudadsFiltrados != null) {
			i = ciudadsFiltrados.size();
		}
		return i;
	}


}