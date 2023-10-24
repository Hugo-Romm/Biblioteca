package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Carro;
import com.infoIV.biblioteca.repository.Carros;
import com.infoIV.biblioteca.repository.filter.CarroFilter;
import com.infoIV.biblioteca.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaCarrosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Carros carros;
	
	private CarroFilter filtro;
	private List<Carro> carrosFiltrados;
	
	private Carro carroSelecionado;
	
	public PesquisaCarrosBean() {
		filtro = new CarroFilter();
	}
	
	public void excluir() {
		carros.remover(carroSelecionado);
		carrosFiltrados.remove(carroSelecionado);
		
		FacesUtil.addInfoMessage("Carro " + carroSelecionado.getCodigo() 
				+ " excluído com suceso.");
	}
	
	public void pesquisar() {
		carrosFiltrados = carros.filtrados(filtro);
	}
	
	public List<Carro> getCarrosFiltrados() {
		return carrosFiltrados;
	}

	public CarroFilter getFiltro() {
		return filtro;
	}

	public Carro getCarroSelecionado() {
		return carroSelecionado;
	}

	public void setCarroSelecionado(Carro carroSelecionado) {
		this.carroSelecionado = carroSelecionado;
	}
	
}