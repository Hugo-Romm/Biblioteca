package com.infoIV.biblioteca.repository;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.infoIV.biblioteca.model.Editor;
import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.repository.filter.LectorFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;
import com.infoIV.biblioteca.util.jsf.FacesUtil;

public class Lectors implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;


	public Lector guardar(Lector lector) {

		return manager.merge(lector);

	}

	@Transactional
	public void remover(Lector lector) {
		try {
			lector = porId(lector.getCodigo());
			manager.remove(lector);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException(
					"No se pudo eliminar el Lector. Verifique!.");
		}
	}
@Transactional
	@SuppressWarnings("unchecked")
	public List<Lector> parcial() {
		
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lector> criteriaQuery = builder.createQuery(Lector.class);
		
		
		Root<Lector> lector = criteriaQuery.from(Lector.class);
		criteriaQuery.select(lector);
		
		TypedQuery<Lector> query =manager.createQuery(criteriaQuery);
		List<Lector> lectores = query.getResultList();
			
		for (Lector lector2 : lectores) {
			
			try {
				Lector lectorx =  porId(lector2.getCodigo());
				manager.remove(lectorx);
				manager.flush();
		
			} catch (PersistenceException e) {
				throw new NegocioException(
						"No se pudo eliminar el Lector. Verifique!.");
			}
				
		}
		
		return lectores;
		
	}

	// @SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	public List<Lector> filtrados(LectorFilter filtro) {
		try {				
			String sql = "FROM Lector  WHERE upper(nombre) LIKE :valor";

			List<Lector> results = manager.createQuery(sql, Lector.class)
			.setParameter("valor",  ("%"+filtro.getNombre().toUpperCase())+"%")
			.getResultList();
			
			return results;
			
		} catch (NoResultException e) {
			return null;
		}		 
	 				
	}
	/****************   LISTADO DE LECTOR (INFORME DE LECTOR)  **************************/
	@SuppressWarnings({ "unchecked" })
	public List<Lector> filtradosReportesLectorTeste2(Lector lector,Long codigoInicio, Long codigoFin,String descriInicio, String descriFin, Integer console ) {
		Session session = manager.unwrap(Session.class);
	    Criteria criteriaLec = session.createCriteria(Lector.class);
	    		
	    System.out.println("console : "+ console);
	    if ((codigoInicio != null)&&(codigoFin != null)){
	  		System.out.println("Entró en codiRestriccion");
	  		 criteriaLec.add(Restrictions.between("codigo", codigoInicio, codigoFin));
	  		
	    }else {
	    	System.out.println("sino de codiRestriccion");
	    }
	  
	  
	    if ((StringUtils.isNotBlank(descriInicio)) && (StringUtils.isNotBlank(descriFin))){
	  		System.out.println("Entró en descriRestriccion");
			criteriaLec.add(Restrictions.between("nombre", descriInicio, descriFin+"z")) ;
			
	    }else {
	    	System.out.println("sino de descriRestriccion");
	    }
	
	       
	    if ((console != null)){
	    	  System.out.println("entra console");
	    	  if (console == 1){
	    		  System.out.println("entra 1");
	    		  return criteriaLec.addOrder(Order.asc("codigo")).list();
	  	       }
	    	  if (console == 2){
	    		  System.out.println("entra 2");
	    		  return criteriaLec.addOrder(Order.asc("nombre")).list();
		  	  }
	    }
	    return criteriaLec.list();
	}
	
/****************************************************************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Lector> filtradoNombreLector(Lector lector, String lecInicio, String lecFin ) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Lector.class);
		System.out.println("desde filtradoporNombre del Lector");

		System.out.println(lecInicio);
		System.out.println(lecFin);

		criteria.add(Restrictions.between("nombre", lecInicio, lecFin)) ;
		
		
	/*	criteria.add(Restrictions.ge("fecpre", dataInicio)); 
		criteria.add(Restrictions.lt("fecpre", dataFim));*/
		
		return criteria.list();
	}

	public Lector porId(Long codigo) {
		return manager.find(Lector.class, codigo);
	}

	public Lector porNome(String nombre) {
		try {
			return manager
					.createQuery("from Lector where upper(nombre) = :nombre",
							Lector.class)
					.setParameter("nombre", nombre.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void porMax() {
		System.out.println("hola");

	}

	public List<Lector> lector() {
		// TODO filtrar apenas func (por um grupo específico)
		return this.manager.createQuery("from Lector", Lector.class)
				.getResultList();
	}
	public List<Lector> lista() {
		// TODO filtrar apenas func (por um grupo específico)
		return this.manager.createQuery("from Lector", Lector.class)
				.getResultList();
	}

}
