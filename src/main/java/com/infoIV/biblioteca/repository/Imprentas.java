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

import com.infoIV.biblioteca.model.Imprenta;
import com.infoIV.biblioteca.model.Libro;

import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class Imprentas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Imprenta guardar(Imprenta imprenta) {
				
		return  manager.merge(imprenta);
				 
	}
	
	@Transactional
	public void remover(Imprenta imprenta) {
		try {
			imprenta = porId(imprenta.getCodigo());
			manager.remove(imprenta);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("No se pudo eliminar ésta Imprenta. Verifique!.");
		}
	}

	
	
  /********   Para el formulario de Imprenta  *************/
	public Imprenta porId(Long codigo) {
		return manager.find(Imprenta.class, codigo);
	}
	
	public Imprenta porNome(String razon_social) {
		try {
			return manager.createQuery("from Imprenta where upper(razonsocial) = :razonsocial", Imprenta.class)
				.setParameter("razonsocial", razon_social.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	/*PARA DESDE LIBROS*/
	
	public List<Imprenta> lista() {
		// TODO filtrar apenas func  (por um grupo específico)
		return this.manager.createQuery("from Imprenta", Imprenta.class)
				.getResultList();
	}

	/****************   LISTADO DE IMPRENTA  **************************/
	@SuppressWarnings({ "unchecked" })
	public List<Imprenta> filtradosReportesImprentaTeste2(Imprenta imprenta,Long codigoInicio, Long codigoFin,String razon_socialInicio, String razon_socialFin, Integer console ) {
		Session session = manager.unwrap(Session.class);
	    Criteria criteriaEdit = session.createCriteria(Imprenta.class);
	    		
	    		
	    if ((codigoInicio != null)&&(codigoFin != null)){
	  		System.out.println("Entró en codiRestriccion");
	  		 criteriaEdit.add(Restrictions.between("codigo", codigoInicio, codigoFin));
	  		
	    }else {
	    	System.out.println("sino de codiRestriccion");
	    }
	  
		if (StringUtils.isNotBlank(razon_socialInicio)) {
			criteriaEdit.add(Restrictions.ge("razon_social", razon_socialInicio));
		}
		
		if (StringUtils.isNotBlank(razon_socialFin)) {
			criteriaEdit.add(Restrictions.le("razon_social", razon_socialFin+"z"));
		}
		  
	    if ((console != null)){
	    	  System.out.println("entra console");
	    	  if (console == 1){
	    		  System.out.println("entra 1");
	    		  return criteriaEdit.addOrder(Order.asc("codigo")).list();
	  	       }
	    	  if (console == 2){
	    		  System.out.println("entra 2");
	    		  return criteriaEdit.addOrder(Order.asc("razon_social")).list();
		  	  }
	    }
	    return criteriaEdit.list();
	}
	
/****************************************************************************************/
	

	}



	
	


	

