package com.infoIV.biblioteca.repository;

import java.io.Serializable;
import java.util.List;

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

import com.infoIV.biblioteca.model.Docente;


import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class Docentes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Docente guardar(Docente docente) {
				
		return  manager.merge(docente);
				 
	}
	
	@Transactional
	public void remover(Docente docente) {
		try {
			docente = porId(docente.getCodigo());
			manager.remove(docente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("No se pudo eliminar Docente. Verifique!.");
		}
	}

	
	
  /********   Para el formulario de Docente  *************/
	public Docente porId(Long codigo) {
		return manager.find(Docente.class, codigo);
	}
	
	public Docente porNome(String nombre) {
		try {
			return manager.createQuery("from Docente where upper(nombre) = :nombre", Docente.class)
				.setParameter("nombre", nombre.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	/*PARA DESDE LIBROS*/
	
	public List<Docente> lista() {
		// TODO filtrar apenas func  (por um grupo específico)
		return this.manager.createQuery("from Docente", Docente.class)
				.getResultList();
	}

	/****************   LISTADO DE EDITORIAL  **************************/
	@SuppressWarnings({ "unchecked" })
	public List<Docente> filtradosReportesDocenteTeste2(Docente docente,Long codigoInicio, Long codigoFin,String nombreInicio, String nombreFin, Integer console ) {
		Session session = manager.unwrap(Session.class);
	    Criteria criteriaEdit = session.createCriteria(Docente.class);
	    		
	    		
	    if ((codigoInicio != null)&&(codigoFin != null)){
	  		System.out.println("Entró en codiRestriccion");
	  		 criteriaEdit.add(Restrictions.between("codigo", codigoInicio, codigoFin));
	  		
	    }else {
	    	System.out.println("sino de codiRestriccion");
	    }
	  
		if (StringUtils.isNotBlank(nombreInicio)) {
			criteriaEdit.add(Restrictions.ge("nombre", nombreInicio));
		}
		
		if (StringUtils.isNotBlank(nombreFin)) {
			criteriaEdit.add(Restrictions.le("nombre", nombreFin+"z"));
		}
		  
	    if ((console != null)){
	    	  System.out.println("entra console");
	    	  if (console == 1){
	    		  System.out.println("entra 1");
	    		  return criteriaEdit.addOrder(Order.asc("codigo")).list();
	  	       }
	    	  if (console == 2){
	    		  System.out.println("entra 2");
	    		  return criteriaEdit.addOrder(Order.asc("nombre")).list();
		  	  }
	    }
	    return criteriaEdit.list();
	}
	
/****************************************************************************************/
	

	}



	
	


	

