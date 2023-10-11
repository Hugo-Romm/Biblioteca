package com.infoIV.biblioteca.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Imprenta;
import com.infoIV.biblioteca.service.CadastroImprentaService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
@ManagedBean
@Named
@ViewScoped
public class CadastroImprentaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	

	@Inject
	private CadastroImprentaService cadastroImprentaService;

	private Imprenta imprenta;
	

	public CadastroImprentaBean() {
		limpar();
	}

	public void inicializar() {
		/* System.out.println("Inicializando..."); */

		if (FacesUtil.isNotPostback()) {
			
		}
	}

	

	private void limpar() {
		imprenta = new Imprenta();
		
	}

	public void salvar() {
		this.imprenta = (Imprenta) cadastroImprentaService.salvar(this.imprenta);
		limpar();
		FacesUtil.addInfoMessage("Imprenta se agregó correctamente!");
	}

	public Imprenta getImprenta() {
		return imprenta;
	}


	
	
	public boolean isEditando() {
		return this.imprenta.getCodigo() != null;
	}

	public void setImprenta(Imprenta imprenta) {
		this.imprenta = imprenta;
		
	}

}