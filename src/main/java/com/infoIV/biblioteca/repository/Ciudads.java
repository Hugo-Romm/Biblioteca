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
import com.infoIV.biblioteca.repository.filter.CiudadFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class Ciudads implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Ciudad guardar(Ciudad editor) {
				
		return  manager.merge(editor);
				 
	}
	
	@Transactional
	public void remover(Ciudad editor) {
		try {
			editor = porId(editor.getCodigo());
			manager.remove(editor);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("No se pudo eliminar ésta Ciudad. Verifique!.");
		}
	}

	
	
  /********   Para el formulario de Ciudad  *************/
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
	
	/****************   LISTADO DE registros  **************************/
	@SuppressWarnings("unchecked")
	public List<Ciudad> filtrados(CiudadFilter filtro) {
		
		
		 
			try {				
				String sql = "FROM Ciudad  WHERE upper(descri) LIKE :valor";

				List<Ciudad> results = manager.createQuery(sql, Ciudad.class)
				.setParameter("valor",  ("%"+filtro.getDescri().toUpperCase())+"%")
				.getResultList();
				
				return results;
				
			} catch (NoResultException e) {
				return null;
			}		  
		 
	}


	/****************   LISTADO DE EDITORIAL  **************************/
	@SuppressWarnings({ "unchecked" })
	public List<Ciudad> filtradosReportesCiudadTeste2(Ciudad editor,Long codigoInicio, Long codigoFin,String descriInicio, String descriFin, Integer console ) {
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
	

	}



	
	


	

