	package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.infoIV.biblioteca.model.Config;
import com.infoIV.biblioteca.model.Deuda;
import com.infoIV.biblioteca.model.Editor;
import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.Configs;
import com.infoIV.biblioteca.repository.Iniciar;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jsf.FacesUtil;

@Named
@ViewScoped
public class limpiarTablaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private EntityManager manager;
    @Inject
    private Iniciar xx;
    @Inject
    private Configs configs;
    
	private Lector xlector ;
	private Editor xeditor ;
	private Libro xlibro ;
	private Deuda xdeuda ;
	private Prestamo xprestamo ;
	private Config xconfig ;
	private String señaVeri;
	private List<Config> correcto;


	public limpiarTablaBean() {
		
	}
   
	public void inicializar() {
		System.out.println("Inicializando..."); 
		limpar();
	}

	public void verificaSeña() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("seña_veri", this.señaVeri);
		señaVeri = this.señaVeri;
	
		System.out.println(señaVeri);
		correcto = configs.controlaSeña(xconfig, señaVeri);
		
		if (correcto.isEmpty() ){
		       System.out.println("La seña ingresada no es correcta");
		       throw new NegocioException("La seña ingresada no es correcta");  
		      
		}else {
			System.out.println("La seña ingresada es correcta");
			FacesUtil.addInfoMessage("La seña ingresada es correcta"); 
		}
		
	}
	public List<Config> getCorrecto() {
		return correcto;
	}
	private void limpar() {
		xlector = new Lector();	
		xlibro = new Libro();	
		xeditor = new Editor();	
		xprestamo = new Prestamo();	
		xdeuda = new Deuda();	
		xconfig = new Config();	
	}

    public void testeConfig(){
        System.out.println("Actualizar Config");
        xx.limpiezaConfig();
        
    }

	public void parcial1() {
	    System.out.println("Parcial");
		xx.parcial();
		FacesUtil.addInfoMessage("Limpieza Parcial de Base de Datos hecha satisfactoriamente!!");
		}
	public void total1() {
	    System.out.println("Total");
		xx.total();
		FacesUtil.addInfoMessage("Limpieza Total de Base de Datos hecha satisfactoriamente!!");
	}
	
	public String getSeñaVeri() {
		return señaVeri;
	}

	public void setSeñaVeri(String señaVeri) {
		this.señaVeri = señaVeri;
	}
	public Lector getLector() {
		return xlector;
	}

	public void setLector(Lector xlector) {
		this.xlector = xlector;
		
	}
	public Editor getEditor() {
		return xeditor;
	}

	public void setEditor(Editor xeditor) {
		this.xeditor = xeditor;
		
	}
	public Libro getLibro() {
		return xlibro;
	}

	public void setLibro(Libro xlibro) {
		this.xlibro = xlibro;
		
	}
	public Prestamo getPrestamo() {
		return xprestamo;
	}

	public void setPrestamo(Prestamo xprestamo) {
		this.xprestamo = xprestamo;
		
	}
	public Deuda getDeuda() {
		return xdeuda;
	}

	public void setDeuda(Deuda xdeuda) {
		this.xdeuda = xdeuda;
		
	}
	public Config getConfig() {
		return xconfig;
	}

	public void setConfig(Config xconfig) {
		this.xconfig = xconfig;
		
	}
	
	}