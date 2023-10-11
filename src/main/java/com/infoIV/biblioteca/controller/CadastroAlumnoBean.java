package com.infoIV.biblioteca.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Alumno;
import com.infoIV.biblioteca.service.CadastroAlumnoService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
@ManagedBean
@Named
@ViewScoped
public class CadastroAlumnoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	

	@Inject
	private CadastroAlumnoService cadastroAlumnoService;

	private Alumno alumno;
	

	public CadastroAlumnoBean() {
		limpar();
	}

	public void inicializar() {
		/* System.out.println("Inicializando..."); */

		if (FacesUtil.isNotPostback()) {
			
		}
	}

	

	private void limpar() {
		alumno = new Alumno();
		
	}

	public void salvar() {
		this.alumno = (Alumno) cadastroAlumnoService.salvar(this.alumno);
		limpar();
		FacesUtil.addInfoMessage("Alumno se agregó correctamente!");
	}

	public Alumno getAlumno() {
		return alumno;
	}


	
	
	public boolean isEditando() {
		return this.alumno.getCodigo() != null;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
		
	}

}