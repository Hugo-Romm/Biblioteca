package com.infoIV.biblioteca.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Ciudad;
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

}