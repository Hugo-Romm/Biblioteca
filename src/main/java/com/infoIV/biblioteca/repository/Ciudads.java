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

import com.infoIV.biblioteca.model.Ciudad;
import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class Ciudads implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Ciudad guardar(Ciudad ciudad) {
				
		return  manager.merge(ciudad);
				 
	}
	
	@Transactional
	public void remover(Ciudad ciudad) {
		try {
			ciudad = porId(ciudad.getCodigo());
			manager.remove(ciudad);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("No se pudo eliminar ésta Ciudadial. Verifique!.");
		}
	}

	
	//@SuppressWarnings("unchecked")
	//@SuppressWarnings("unchecked")
	/*public List<Ciudad> filtrados(CiudadFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Ciudad.class);
		
		if (filtro.getCodigo() != null) {
			criteria.add(Restrictions.ge("codigo", filtro.getCodigo()));
		}
		
		if (StringUtils.isNotBlank(filtro.getDescri())) {
			criteria.add(Restrictions.ilike("descri", filtro.getDescri(), MatchMode.ANYWHERE));
		}
		
		//return criteria.list();
		
		return criteria.addOrder(Order.asc("descri")).list();
	}
  /********   Para el formulario de Ciudadial  *************/
	public Ciudad porId(Long codigo) {
		return manager.find(Ciudad.class, codigo);
	}
	
	public Ciudad porNome(String descri) {
		try {
			return manager.createQuery("from Ciudad where upper(descri) = :descri", Ciudad.class)
				.setParameter("descri", descri.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	/*PARA DESDE LIBROS*/
	
	public List<Ciudad> lista() {
		// TODO filtrar apenas func  (por um grupo específico)
		return this.manager.createQuery("from Ciudad", Ciudad.class)
				.getResultList();
	}
//	public List<Ciudad> subciudadesDe(Ciudad ciudadPadre) {
//		
//		return manager.createQuery("from Ciudad where codigo = :raiz", 
//				Ciudad.class).setParameter("raiz", ciudadPadre).getResultList();
//	}

/*	public List<Ciudad> porNomeLista(String descri) {

		return this.manager.createQuery("from Ciudad " +
				"where upper(descri) like :decri", Ciudad.class)
				.setParameter("descri", descri.toUpperCase() + "%")
				.getResultList();
		
	}*/
	/****************   LISTADO DE EDITORIAL  **************************/
	@SuppressWarnings({ "unchecked" })
	public List<Ciudad> filtradosReportesCiudadTeste2(Ciudad ciudad,Long codigoInicio, Long codigoFin,String descriInicio, String descriFin, Integer console ) {
		Session session = manager.unwrap(Session.class);
	    Criteria criteriaEdit = session.createCriteria(Ciudad.class);
	    		
	    		
	    if ((codigoInicio != null)&&(codigoFin != null)){
	  		System.out.println("Entró en codiRestriccion");
	  		 criteriaEdit.add(Restrictions.between("codigo", codigoInicio, codigoFin));
	  		
	    }else {
	    	System.out.println("sino de codiRestriccion");
	    }
	  
		if (StringUtils.isNotBlank(descriInicio)) {
			criteriaEdit.add(Restrictions.ge("descri", descriInicio));
		}
		
		if (StringUtils.isNotBlank(descriFin)) {
			criteriaEdit.add(Restrictions.le("descri", descriFin+"z"));
		}
		  
	    if ((console != null)){
	    	  System.out.println("entra console");
	    	  if (console == 1){
	    		  System.out.println("entra 1");
	    		  return criteriaEdit.addOrder(Order.asc("codigo")).list();
	  	       }
	    	  if (console == 2){
	    		  System.out.println("entra 2");
	    		  return criteriaEdit.addOrder(Order.asc("descri")).list();
		  	  }
	    }
	    return criteriaEdit.list();
	}
	
/****************************************************************************************/
	
/*	public List<Ciudad> lista() {
		return manager.createQuery("from Ciudad", Ciudad.class).getResultList();
	}
	
	public List<Ciudad> subciudadsDe(Ciudad codigo) {
		return manager.createQuery("from Ciudad", Ciudad.class).getResultList();

	}*/
	}



	
	


	

