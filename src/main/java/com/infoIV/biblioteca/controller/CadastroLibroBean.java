package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Editor;
import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.repository.Editors;
import com.infoIV.biblioteca.repository.Libros;
import com.infoIV.biblioteca.repository.filter.LibroFilter;
import com.infoIV.biblioteca.service.CadastroLibroService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
@ManagedBean
@Named
@ViewScoped
public class CadastroLibroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Editors editors;

	@Inject
	private CadastroLibroService cadastroLibroService;
	
	@Inject
	private Libros libros = new Libros();

	private Libro libro;
//	private Editor editorPadre;
	
	private Libro libroSelecionado;
	private LibroFilter filtro;
	private List<Libro> librosFiltrados;

	private List<Editor> editorRaiz;
	private List<Editor> subeditores;
	

	public CadastroLibroBean() {
		limpar();
		filtro = new LibroFilter();
	}

	public void inicializar() {
		
		if (FacesUtil.isNotPostback()) {
			
				carregarSubeditors();
		  
		}
	}

	public void carregarSubeditors() {
		subeditores = editors.lista();
	}

	private void limpar() {
		libro = new Libro();
//		editorPadre= null;
	
	}

	public void salvar() {
		this.libro = (Libro) cadastroLibroService.salvar(this.libro);
		
		limpar();

		FacesUtil.addInfoMessage("Libro se agregó con éxito!");
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
		if (this.libro != null) {
//			this.editorPadre = this.libro.getEditor();
		}
	}
	
//	public List<Editor> getEditorRaiz() {
//		return editorRaiz;
//	}
	
//	@NotNull
//	public Editor getEditorPadre() {
//		return editorPadre;
//	}
//
//	public void setEditorPadre(Editor editorPadre) {
//		this.editorPadre = this.libro.getEditor();
//	}
/*
	public Editors getEditors() {
		return editors;
	}*/

	public List<Editor> getSubeditores() {
		return subeditores;
	}
	
	
	public boolean isEditando() {
		return this.libro.getCodigo() != null;
	}
	
	public Libro getLibroSelecionado() {
		return libroSelecionado;
	}

	public void setLibroSelecionado(Libro libroSelecionado) {
		this.libroSelecionado = libroSelecionado;
	}

	public LibroFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(LibroFilter filtro) {
		this.filtro = filtro;
	}

	public List<Libro> getLibrosFiltrados() {
		return librosFiltrados;
	}


    public void excluir() {
    	libros.remover(libroSelecionado);
		librosFiltrados.remove(libroSelecionado);    		
		
		FacesUtil.addInfoMessage("Editor " + libroSelecionado.getCodigo()
				+ " eliminado con éxito.");
	}	
		
	


	public void pesquisar() {
		
		if (filtro.getDescri().isEmpty()) {			
			librosFiltrados = libros.lista();
		}else {
			librosFiltrados = libros.filtrados(filtro);		
		}

	}
	
	public int getCantidad() {
		int i = 0;
		if (librosFiltrados != null) {
			i = librosFiltrados.size();
		}
		return i;
	}


}