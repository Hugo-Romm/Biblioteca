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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.infoIV.biblioteca.model.Editor;
import com.infoIV.biblioteca.repository.filter.EditorFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

import org.hibernate.query.Query;


public class Editors implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Editor guardar(Editor editor) {
				
		return  manager.merge(editor);
				 
	}
	
	@Transactional
	public void remover(Editor editor) {
		try {
			editor = porId(editor.getCodigo());
			manager.remove(editor);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("No se pudo eliminar ésta Editorial. Verifique!.");
		}
	}

	
	//@SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	public List<Editor> filtrados(EditorFilter filtro) {
		
		
		 
			try {				
				String sql = "FROM Editor  WHERE upper(descri) LIKE :valor";

				List<Editor> results = manager.createQuery(sql, Editor.class)
				.setParameter("valor",  ("%"+filtro.getDescri().toUpperCase())+"%")
				.getResultList();
				
				return results;
				
			} catch (NoResultException e) {
				return null;
			}		 
		 
		 
	}
  /********   Para el formulario de Editorial  *************/
	public Editor porId(Long codigo) {
		return manager.find(Editor.class, codigo);
	}
	
	public Editor porNome(String descri) {
		try {
			return manager.createQuery("from Editor where upper(descri) = :descri", Editor.class)
				.setParameter("descri", descri.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	/*PARA DESDE LIBROS*/
	
	public List<Editor> lista() {
		// TODO filtrar apenas func  (por um grupo específico)
		
		return this.manager.createQuery("from Editor", Editor.class)
				.getResultList();
	}


	/****************   LISTADO DE EDITORIAL  **************************/
	@SuppressWarnings({ "unchecked" })
	public List<Editor> filtradosReportesEditorTeste2(Editor editor,Long codigoInicio, Long codigoFin,String descriInicio, String descriFin, Integer console ) {
		Session session = manager.unwrap(Session.class);
	    Criteria criteriaEdit = session.createCriteria(Editor.class);
	    		
	    		
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


	
	


	

