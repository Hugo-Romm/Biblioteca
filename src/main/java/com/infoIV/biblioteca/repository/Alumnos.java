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

import com.infoIV.biblioteca.model.Alumno;


import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class Alumnos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Alumno guardar(Alumno alumno) {
				
		return  manager.merge(alumno);
				 
	}
	
	@Transactional
	public void remover(Alumno alumno) {
		try {
			alumno = porId(alumno.getCodigo());
			manager.remove(alumno);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("No se pudo eliminar Alumno. Verifique!.");
		}
	}

	
	
  /********   Para el formulario de Alumno  *************/
	public Alumno porId(Long codigo) {
		return manager.find(Alumno.class, codigo);
	}
	
	public Alumno porNome(String nombre) {
		try {
			return manager.createQuery("from Alumno where upper(nombre) = :nombre", Alumno.class)
				.setParameter("nombre", nombre.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	/*PARA DESDE LIBROS*/
	
	public List<Alumno> lista() {
		// TODO filtrar apenas func  (por um grupo específico)
		return this.manager.createQuery("from Alumno", Alumno.class)
				.getResultList();
	}

	/****************   LISTADO DE EDITORIAL  **************************/
	@SuppressWarnings({ "unchecked" })
	public List<Alumno> filtradosReportesAlumnoTeste2(Alumno alumno,Long codigoInicio, Long codigoFin,String nombreInicio, String nombreFin, Integer console ) {
		Session session = manager.unwrap(Session.class);
	    Criteria criteriaEdit = session.createCriteria(Alumno.class);
	    		
	    		
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



	
	


	

