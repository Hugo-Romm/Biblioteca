package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.infoIV.biblioteca.model.Config;
import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.Configs;
import com.infoIV.biblioteca.repository.Lectors;
import com.infoIV.biblioteca.repository.Libros;
import com.infoIV.biblioteca.repository.Prestamos;
import com.infoIV.biblioteca.repository.filter.PrestamoFilter;
import com.infoIV.biblioteca.service.CadastroPrestamoService;
import com.infoIV.biblioteca.util.jpa.Transactional;
import com.infoIV.biblioteca.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroPrestamoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Lectors lectors;
	@Inject
	private Libros libros;

	
	@Inject
	private CadastroPrestamoService cadastroPrestamoService;
	
	@Inject
	private Prestamos prestamos = new Prestamos();

	private Prestamo prestamo;
	private Config config;	
	private PrestamoFilter filtro;
	private List<Prestamo> prestamosFiltrados;
//	private Editor editorPadre;
	private String s ;
	@Inject
	private Configs configs ;
	@Inject
	private EntityManager manager;
	private List<Lector> lectorRaiz;
	private List<Lector> sublectores;
	
	private List<Libro> libroRaiz;
	private List<Libro> sublibros;

	public CadastroPrestamoBean() {
		filtro = new PrestamoFilter();
		limpar();
		
	}
	

	public void calcularFechaDevolucion1(){
		System.out.println("Calcular Devolucion");
		this.prestamo.calcularFechaDevolucion();
	}
	@SuppressWarnings("unchecked")
	@Transactional
    public Integer diasAPrestar(){
		    Integer x = 0;
    		System.out.println("Dias a prestar");
    		Query query = manager.createNativeQuery("Select * from config",
    				Config.class);
    		List<Config> conf = query.getResultList();

    		for (Config configs : conf) {
    			x =configs.getDiapre();
    			System.out.println("Codigo = " + configs.getCodigo()
    					+ "Dias Prestamo =" + configs.getDiapre());

    		}
    		return x ;

    	}
	public void inicializar() {
		System.out.println("Inicializando..."); 
		if (FacesUtil.isNotPostback()) {
			
			cargarSublectors();
			cargarSublibros();
			

		}
	}
	public void cargarSublectors() {
		sublectores = lectors.lista();
		System.out.println("Cargando Sublectores!");
	}
	public void cargarSublibros() {
		sublibros = libros.lista();
		System.out.println("Cargando Sublibros!");
	}
	public List<Prestamo> getPrestamosFiltrados() {
		return prestamosFiltrados;
	}

	public PrestamoFilter getFiltro() {
		return filtro;
	}
	private void limpar() {
		prestamo = new Prestamo();
		
	}
	
	public void salvar() {
		
		System.out.println("Codigo del libro"+this.prestamo.getLibro().getCodigo()+"desde CadastroPrestamoBean");
		this.prestamo = cadastroPrestamoService.verificar(this.prestamo);
		/*this.prestamo = cadastroPrestamoService.salvar(this.prestamo);*/
		limpar();

		FacesUtil.addInfoMessage("Prestamo se agregó con éxito!");
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
		if (this.prestamo != null) {
			// this.lectorPadre = this.prestamo.getLector();
		}
	}
	
	public List<Lector> getSublectores() {
		return sublectores;
	}
	public List<Libro> getSublibros() {
		return sublibros;
	}
	
	public Date getFechaHoy() {
		return new Date();
	}
	public Date getFechaDev() {
		return this.prestamo.getFecpre();
	}
	public void fechaDevolucion(){
		
	}


	public boolean isEditando() {
		return this.prestamo.getNumero() != null;
	}
	public boolean isDevolver() {
		
		return false;
	}
	public List<Lector> sugerirLector(String nombre) {
		List<Lector> lectorSugerido = new ArrayList<>();

		for (Lector lector : sublectores) {
			if (lector.getNombre().toLowerCase()
					.startsWith(nombre.toLowerCase())) {
				lectorSugerido.add(lector);
				System.out.println(nombre);
				Lector lectorx = lectors.porId(lector.getCodigo());
				System.out.println(lectorx.getCodigo());
				System.out.println(lectorx.getNombre());
				System.out.println("Sugerir Lector");

			}
		}

		return lectorSugerido;

	}

	public List<Libro> sugerirLibro(String descri) {
		List<Libro> libroSugerido = new ArrayList<>();

		for (Libro libro : sublibros) {
			if (libro.getDescri().toLowerCase()
					.startsWith(descri.toLowerCase())) {
				libroSugerido.add(libro);
			}
		}

		return libroSugerido;
	}
}
	
	


	
	