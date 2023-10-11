package com.infoIV.biblioteca.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Docente;
import com.infoIV.biblioteca.service.CadastroDocenteService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
@ManagedBean
@Named
@ViewScoped
public class CadastroDocenteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	

	@Inject
	private CadastroDocenteService cadastroDocenteService;

	private Docente docente;
	

	public CadastroDocenteBean() {
		limpar();
	}

	public void inicializar() {
		/* System.out.println("Inicializando..."); */

		if (FacesUtil.isNotPostback()) {
			
		}
	}

	

	private void limpar() {
		docente = new Docente();
		
	}

	public void salvar() {
		this.docente = (Docente) cadastroDocenteService.salvar(this.docente);
		limpar();
		FacesUtil.addInfoMessage("Docente se agregó correctamente!");
	}

	public Docente getDocente() {
		return docente;
	}


	
	
	public boolean isEditando() {
		return this.docente.getCodigo() != null;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
		
	}

}