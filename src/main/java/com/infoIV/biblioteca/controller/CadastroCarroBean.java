package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Docente;
import com.infoIV.biblioteca.model.Carro;
import com.infoIV.biblioteca.repository.Docentes;
import com.infoIV.biblioteca.repository.Carros;
import com.infoIV.biblioteca.repository.filter.CarroFilter;
import com.infoIV.biblioteca.service.CadastroCarroService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
@ManagedBean
@Named
@ViewScoped
public class CadastroCarroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Docentes docentes;

	@Inject
	private CadastroCarroService cadastroCarroService;
	
	@Inject
	private Carros carros = new Carros();

	private Carro carro;
//	private Docente docentePadre;
	
	private Carro carroSelecionado;
	private CarroFilter filtro;
	private List<Carro> carrosFiltrados;

	private List<Docente> docenteRaiz;
	private List<Docente> subdocentees;
	

	public CadastroCarroBean() {
		limpar();
		filtro = new CarroFilter();
	}

	public void inicializar() {
		
		if (FacesUtil.isNotPostback()) {
			
				carregarSubdocentes();
		  
		}
	}

	public void carregarSubdocentes() {
		subdocentees = docentes.lista();
	}

	private void limpar() {
		carro = new Carro();
//		docentePadre= null;
	
	}

	public void salvar() {
		this.carro = (Carro) cadastroCarroService.salvar(this.carro);
		
		limpar();

		FacesUtil.addInfoMessage("Carro se agregó con éxito!");
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
		if (this.carro != null) {
//			this.docentePadre = this.carro.getDocente();
		}
	}
	
//	public List<Docente> getDocenteRaiz() {
//		return docenteRaiz;
//	}
	
//	@NotNull
//	public Docente getDocentePadre() {
//		return docentePadre;
//	}
//
//	public void setDocentePadre(Docente docentePadre) {
//		this.docentePadre = this.carro.getDocente();
//	}
/*
	public Docentes getDocentes() {
		return docentes;
	}*/

	public List<Docente> getSubdocentees() {
		return subdocentees;
	}
	
	
	public boolean isEditando() {
		return this.carro.getCodigo() != null;
	}
	
	public Carro getCarroSelecionado() {
		return carroSelecionado;
	}

	public void setCarroSelecionado(Carro carroSelecionado) {
		this.carroSelecionado = carroSelecionado;
	}

	public CarroFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(CarroFilter filtro) {
		this.filtro = filtro;
	}

	public List<Carro> getCarrosFiltrados() {
		return carrosFiltrados;
	}


    public void excluir() {
    	carros.remover(carroSelecionado);
		carrosFiltrados.remove(carroSelecionado);    		
		
		FacesUtil.addInfoMessage("Docente " + carroSelecionado.getCodigo()
				+ " eliminado con éxito.");
	}	
		
	


	public void pesquisar() {
		
		if (filtro.getMarca().isEmpty()) {			
			carrosFiltrados = carros.lista();
		}else {
			carrosFiltrados = carros.filtrados(filtro);		
		}

	}
	
	public int getCantidad() {
		int i = 0;
		if (carrosFiltrados != null) {
			i = carrosFiltrados.size();
		}
		return i;
	}


}