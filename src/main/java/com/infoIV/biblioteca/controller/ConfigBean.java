package com.infoIV.biblioteca.controller;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Config;
import com.infoIV.biblioteca.service.CadastroConfigService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ConfigBean implements Serializable {

	private static final long serialVersionUID = 1L;

	


	@Inject
	private CadastroConfigService cadastroConfigService;
	@Inject
	private Config config;
	private String empresa;

	public ConfigBean() {
		limpar();
	}

	public void inicializar() {
		/* System.out.println("Inicializando..."); */
        System.out.println("Inicializar"+this.config.getSeña());
        
       
		if (FacesUtil.isNotPostback()) {
			
		}
	}
	public void mostrar(){
		System.out.println("entro");	
		
		empresa = this.config.getOrganom();
	}
	private void limpar() {
		config = new Config();
		
	}

	public void salvar() {
		this.config = cadastroConfigService.salvar(this.config);
		/*limpar();*/

		FacesUtil.addInfoMessage("Parámetros agregados corectamente!");
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	
		public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

		public boolean isEditando() {
		return this.config.getCodigo() != null;
	}


		
	}

