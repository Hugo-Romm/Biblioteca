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

import com.infoIV.biblioteca.model.Medida;
import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class Medidas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Medida guardar(Medida medida) {
				
		return  manager.merge(medida);
				 
	}
	
	@Transactional
	public void remover(Medida medida) {
		try {
			medida = porId(medida.getCodigo());
			manager.remove(medida);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("No se pudo eliminar ésta Medidaial. Verifique!.");
		}
	}

	
	//@SuppressWarnings("unchecked")
	/*@SuppressWarnings("unchecked")
	public List<Medida> filtrados(MedidaFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Medida.class);
		
		if (filtro.getCodigo() != null) {
			criteria.add(Restrictions.ge("codigo", filtro.getCodigo()));
		}
		
		if (StringUtils.isNotBlank(filtro.getDescri())) {
			criteria.add(Restrictions.ilike("descri", filtro.getDescri(), MatchMode.ANYWHERE));
		}
		
		//return criteria.list();
		
		return criteria.addOrder(Order.asc("descri")).list();
	}
  /********   Para el formulario de Medidaial  *************/
	public Medida porId(Long codigo) {
		return manager.find(Medida.class, codigo);
	}
	
	public Medida porNome(String descri) {
		try {
			return manager.createQuery("from Medida where upper(descri) = :descri", Medida.class)
				.setParameter("descri", descri.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	/*PARA DESDE LIBROS*/
	
	public List<Medida> lista() {
		// TODO filtrar apenas func  (por um grupo específico)
		return this.manager.createQuery("from Medida", Medida.class)
				.getResultList();
	}
//	public List<Medida> submedidaesDe(Medida medidaPadre) {
//		
//		return manager.createQuery("from Medida where codigo = :raiz", 
//				Medida.class).setParameter("raiz", medidaPadre).getResultList();
//	}

/*	public List<Medida> porNomeLista(String descri) {

		return this.manager.createQuery("from Medida " +
				"where upper(descri) like :decri", Medida.class)
				.setParameter("descri", descri.toUpperCase() + "%")
				.getResultList();
		
	}*/
	/****************   LISTADO DE EDITORIAL  **************************/
	@SuppressWarnings({ "unchecked" })
	public List<Medida> filtradosReportesMedidaTeste2(Medida medida,Long codigoInicio, Long codigoFin,String descriInicio, String descriFin, Integer console ) {
		Session session = manager.unwrap(Session.class);
	    Criteria criteriaEdit = session.createCriteria(Medida.class);
	    		
	    		
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
	
/*	public List<Medida> lista() {
		return manager.createQuery("from Medida", Medida.class).getResultList();
	}
	
	public List<Medida> submedidasDe(Medida codigo) {
		return manager.createQuery("from Medida", Medida.class).getResultList();

	}*/
	}



	
	


	

