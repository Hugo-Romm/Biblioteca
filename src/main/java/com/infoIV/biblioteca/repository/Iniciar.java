package com.infoIV.biblioteca.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.infoIV.biblioteca.model.Config;
import com.infoIV.biblioteca.model.Deuda;
import com.infoIV.biblioteca.model.Editor;
import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.service.CadastroConfigService;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class Iniciar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@Inject
	private CadastroConfigService cadastroConfigService;

	/* LIMPIEZA TOTAL DE BD */
	@Transactional
	public void total() {
		parcial();
		limpiezaLibro();
		limpiezaEditor();
		
		limpiezaLector();

	}

	@Transactional
	public List<Lector> limpiezaLector() {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lector> criteriaQuery = builder.createQuery(Lector.class);

		Root<Lector> lector = criteriaQuery.from(Lector.class);
		criteriaQuery.select(lector);

		TypedQuery<Lector> query = manager.createQuery(criteriaQuery);
		List<Lector> lectores = query.getResultList();

		for (Lector lector2 : lectores) {

			try {
				Lector lectorx = porId(lector2.getCodigo());
				manager.remove(lectorx);
				manager.flush();

			} catch (PersistenceException e) {
				throw new NegocioException(
						"No se pudo eliminar la tabla Lector. Verifique!.");
			}

		}

		return lectores;

	}

	@Transactional
	public List<Editor> limpiezaEditor() {

		CriteriaBuilder builder2 = manager.getCriteriaBuilder();
		CriteriaQuery<Editor> criteriaQuery2 = builder2
				.createQuery(Editor.class);

		Root<Editor> editor = criteriaQuery2.from(Editor.class);
		criteriaQuery2.select(editor);

		TypedQuery<Editor> query2 = manager.createQuery(criteriaQuery2);
		List<Editor> editores = query2.getResultList();

		for (Editor editor2 : editores) {

			try {
				Editor editorx = porId2(editor2.getCodigo());
				manager.remove(editorx);
				manager.flush();

			} catch (PersistenceException e) {
				throw new NegocioException(
						"No se pudo eliminar la tabla Editor. Verifique que no existan prestamos relacionados!.");
			}

		}

		return editores;

	}

	@Transactional
	public List<Libro> limpiezaLibro() {

		CriteriaBuilder builder1 = manager.getCriteriaBuilder();
		CriteriaQuery<Libro> criteriaQuery1 = builder1.createQuery(Libro.class);

		Root<Libro> libro = criteriaQuery1.from(Libro.class);
		criteriaQuery1.select(libro);

		TypedQuery<Libro> query1 = manager.createQuery(criteriaQuery1);
		List<Libro> libros = query1.getResultList();

		for (Libro libro2 : libros) {

			try {
				Libro librox = porId3(libro2.getCodigo());
				manager.remove(librox);
				manager.flush();

			} catch (PersistenceException e) {
				throw new NegocioException(
						"No se pudo eliminar la tabla Libro. Verifique!.");
			}

		}

		return libros;

	}

	public Lector porId(Long codigo) {
		return manager.find(Lector.class, codigo);
	}

	public Editor porId2(Long codigo) {
		return manager.find(Editor.class, codigo);
	}

	public Libro porId3(Long codigo) {
		return manager.find(Libro.class, codigo);
	}

	/* LIMPIEZA PARCIAL DE BD */
	@Transactional
	public void parcial() {
		limpiezaDeuda();
		limpiezaPrestamo();
	
		 limpiezaConfig();
	}
	@SuppressWarnings("unchecked")
	public void verificaSeñaConfig() {
		System.out.println("Verificando Contraseña");
		Query query1 = manager.createNativeQuery("Select * from config",
				Config.class);
		List<Config> conf1 = query1.getResultList();
		
		for (Config configs : conf1) {
			
			configs = (Config) cadastroConfigService.salvar(configs);
			System.out.println("Codigo = " + configs.getCodigo()
					+ "Dias Prestamo =" + configs.getSeña());

		}

	}
	@SuppressWarnings("unchecked")
	@Transactional
	public void limpiezaConfig() {
		System.out.println("entro");
		String vacio = " ";
		Query query = manager.createNativeQuery("Select * from config",
				Config.class);
		List<Config> conf = query.getResultList();

		for (Config configs : conf) {
			configs.setDeumon(0);
			configs.setDiamor(0);
			configs.setDiapre(0);
			configs.setOrganom(vacio);
			configs.setSeña(vacio);
			configs = (Config) cadastroConfigService.salvar(configs);
			System.out.println("Codigo = " + configs.getCodigo()
					+ "Dias Prestamo =" + configs.getDiapre());

		}

	}

	/*
	 * CriteriaBuilder builder = manager.getCriteriaBuilder();
	 * CriteriaQuery<Config> criteriaQuery = builder.createQuery(Config.class);
	 * 
	 * 
	 * Root<Config> config = criteriaQuery.from(Config.class);
	 * criteriaQuery.select(config);
	 * 
	 * TypedQuery<Config> query =manager.createQuery(criteriaQuery);
	 * List<Config> configuraciones = query.getResultList();
	 * 
	 * for (Config config2 : configuraciones) {
	 * 
	 * try { Config configx = porId4(config2.getCodigo());
	 * manager.remove(configx); manager.flush();
	 * 
	 * } catch (PersistenceException e) { throw new NegocioException(
	 * "No se pudo eliminar la tabla Config. Verifique!."); }
	 * 
	 * }
	 * 
	 * return configuraciones;
	 */

	@Transactional
	public List<Deuda> limpiezaDeuda() {

		CriteriaBuilder builder2 = manager.getCriteriaBuilder();
		CriteriaQuery<Deuda> criteriaQuery2 = builder2.createQuery(Deuda.class);

		Root<Deuda> deuda = criteriaQuery2.from(Deuda.class);
		criteriaQuery2.select(deuda);

		TypedQuery<Deuda> query2 = manager.createQuery(criteriaQuery2);
		List<Deuda> deudas = query2.getResultList();

		for (Deuda deuda2 : deudas) {

			try {
				Deuda deudax = porId5(deuda2.getNum());
				manager.remove(deudax);
				manager.flush();

			} catch (PersistenceException e) {
				throw new NegocioException(
						"No se pudo eliminar la tabla Deuda. Verifique que no existan libros relacionados!.");
			}

		}

		return deudas;

	}

	@Transactional
	public List<Prestamo> limpiezaPrestamo() {

		CriteriaBuilder builder1 = manager.getCriteriaBuilder();
		CriteriaQuery<Prestamo> criteriaQuery1 = builder1
				.createQuery(Prestamo.class);

		Root<Prestamo> prestamo = criteriaQuery1.from(Prestamo.class);
		criteriaQuery1.select(prestamo);

		TypedQuery<Prestamo> query1 = manager.createQuery(criteriaQuery1);
		List<Prestamo> prestamos = query1.getResultList();

		for (Prestamo prestamo2 : prestamos) {

			try {
				Prestamo prestamox = porId6(prestamo2.getNumero());
				manager.remove(prestamox);
				manager.flush();

			} catch (PersistenceException e) {
				throw new NegocioException(
						"No se pudo eliminar la tabla Prestamo. Verifique!.");
			}

		}

		return prestamos;

	}

	public Config porId4(Long codigo) {
		return manager.find(Config.class, codigo);
	}

	public Deuda porId5(Long num) {
		return manager.find(Deuda.class, num);
	}

	public Prestamo porId6(Long numero) {
		return manager.find(Prestamo.class, numero);
	}

}
