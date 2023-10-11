package com.infoIV.biblioteca.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Medida;
import com.infoIV.biblioteca.service.CadastroMedidaService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
@ManagedBean
@Named
@ViewScoped
public class CadastroMedidaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	

	@Inject
	private CadastroMedidaService cadastroMedidaService;

	private Medida medida;
	

	public CadastroMedidaBean() {
		limpar();
	}

	public void inicializar() {
		/* System.out.println("Inicializando..."); */

		if (FacesUtil.isNotPostback()) {
			
		}
	}

	

	private void limpar() {
		medida = new Medida();
		
	}

	public void salvar() {
		this.medida = (Medida) cadastroMedidaService.salvar(this.medida);
		limpar();
		FacesUtil.addInfoMessage("Medidaial se agregó correctamente!");
	}

	public Medida getMedida() {
		return medida;
	}

	public void setMedidae(Medida medida) {
		this.medida = medida;
		
	}

	
	
	public boolean isEditando() {
		return this.medida.getCodigo() != null;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
		
	}

}