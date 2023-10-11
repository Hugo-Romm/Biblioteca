package com.infoIV.biblioteca.repository;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.model.Editor;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.filter.LibroFilter;
import com.infoIV.biblioteca.repository.filter.PrestamoFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class Libros implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	@Inject
	private Editor editor;
	private LibroFilter filtro = new LibroFilter();
	private List<Libro> librosFiltrados;
	
	
	@Inject
	private FacesContext facesContext;

	public Libro guardar(Libro libro) {
		return manager.merge(libro);
	}

	@Transactional
	public void remover(Libro libro) {
		try {
			libro = porId(libro.getCodigo());
			manager.remove(libro);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Libro no puede ser excluído.");
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<Libro> filtrados(LibroFilter filtro) {
		
		try {				
			String sql = "FROM Libro  WHERE upper(descri) LIKE :valor";

			List<Libro> results = manager.createQuery(sql, Libro.class)
			.setParameter("valor",  ("%"+filtro.getDescri().toUpperCase())+"%")
			.getResultList();
			
			return results;
			
		} catch (NoResultException e) {
			return null;
		}	

	}

	public Libro porId(Long codigo) {
		return manager.find(Libro.class, codigo);
	}
	
	
	public List<Libro> porNomeLista(String descri) {
		return this.manager.createQuery("from Libro " +
				"where upper(descri) like :descri", Libro.class)
				.setParameter("descri", descri.toUpperCase() + "%")
				.getResultList();
	}
	
	public Libro ultiCod() {
	
		return manager.createQuery("codigo from Libro ",Libro.class)
				.getSingleResult();
	} 
	
	public Libro porNome(String descri) {
		try {
			return manager
					.createQuery(
							"from Libro where upper(descri) = :descri",
							Libro.class)
					.setParameter("descri", descri.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Libro> lista() {
		// TODO filtrar apenas func  (por um grupo específico)
		return this.manager.createQuery("from Libro", Libro.class)
				.getResultList();
	}
	
/****************   LISTADO DE LIBRO (INFORME DE LIBRO)  **************************/
	@SuppressWarnings({ "unchecked" })
	public List<Libro> filtradosReportesLibroTeste2(Libro libro,Long codigoInicio, Long codigoFin,String descriInicio, String descriFin,String editoInicio, String editoFin, Integer console ) {
		Session session = manager.unwrap(Session.class);
	    Criteria criteriaLib = session.createCriteria(Libro.class)
	    		.createAlias("editor", "edi");
	    System.out.println("console : "+ console);
	    if ((codigoInicio != null)&&(codigoFin != null)){
	  		System.out.println("Entró en codiRestriccion");
	  		 criteriaLib.add(Restrictions.between("codigo", codigoInicio, codigoFin));
	  		
	    }else {
	    	System.out.println("sino de codiRestriccion");
	    }
	  
	  
	    if ((StringUtils.isNotBlank(descriInicio)) && (StringUtils.isNotBlank(descriFin))){
	  		System.out.println("Entró en descriRestriccion");
			criteriaLib.add(Restrictions.between("descri", descriInicio, descriFin+"z")) ;
			
	    }else {
	    	System.out.println("sino de descriRestriccion");
	    }
	
	    if ((StringUtils.isNotBlank(editoInicio)) && (StringUtils.isNotBlank(editoFin))){
	    	criteriaLib.add(Restrictions.between("edi.descri", editoInicio, editoFin+"z"));
	    }else {
	    	System.out.println("sino de editoRestriccion");
	    }
	    
	    if ((console != null)){
	    	  System.out.println("entra console");
	    	  if (console == 1){
	    		  System.out.println("entra 1");
	    		  return criteriaLib.addOrder(Order.asc("codigo")).list();
	  	       }
	    	  if (console == 2){
	    		  System.out.println("entra 2");
	    		  return criteriaLib.addOrder(Order.asc("descri")).list();
		  	  }
	    	  if (console == 3){
	    		  System.out.println("entra 3");
	    		  return criteriaLib.addOrder(Order.asc("edi.descri")).list();
		  	  }
	    }else {
	    	System.out.println("sino de editoRestriccion");
	    }
	    return criteriaLib.list();
	}
	
/****************************************************************************************/
}
