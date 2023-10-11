package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.repository.Libros;
import com.infoIV.biblioteca.repository.filter.LibroFilter;
import com.infoIV.biblioteca.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaLibrosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Libros libros;
	
	private LibroFilter filtro;
	private List<Libro> librosFiltrados;
	
	private Libro libroSelecionado;
	
	public PesquisaLibrosBean() {
		filtro = new LibroFilter();
	}
	
	public void excluir() {
		libros.remover(libroSelecionado);
		librosFiltrados.remove(libroSelecionado);
		
		FacesUtil.addInfoMessage("Libro " + libroSelecionado.getCodigo() 
				+ " excluído com suceso.");
	}
	
	public void pesquisar() {
		librosFiltrados = libros.filtrados(filtro);
	}
	
	public List<Libro> getLibrosFiltrados() {
		return librosFiltrados;
	}

	public LibroFilter getFiltro() {
		return filtro;
	}

	public Libro getLibroSelecionado() {
		return libroSelecionado;
	}

	public void setLibroSelecionado(Libro libroSelecionado) {
		this.libroSelecionado = libroSelecionado;
	}
	
}