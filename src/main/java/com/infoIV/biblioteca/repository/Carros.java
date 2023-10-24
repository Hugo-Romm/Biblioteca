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

import com.infoIV.biblioteca.model.Carro;
import com.infoIV.biblioteca.model.Docente;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.filter.CarroFilter;
import com.infoIV.biblioteca.repository.filter.PrestamoFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class Carros implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	@Inject
	private Docente docente;
	private CarroFilter filtro = new CarroFilter();
	private List<Carro> carrosFiltrados;
	
	
	@Inject
	private FacesContext facesContext;

	public Carro guardar(Carro carro) {
		return manager.merge(carro);
	}

	@Transactional
	public void remover(Carro carro) {
		try {
			carro = porCodigo(carro.getCodigo());
			manager.remove(carro);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Carro no puede ser excluído.");
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<Carro> filtrados(CarroFilter filtro) {
		
		try {				
			String sql = "FROM Carro  WHERE upper(marca) LIKE :valor";

			List<Carro> results = manager.createQuery(sql, Carro.class)
			.setParameter("valor",  ("%"+filtro.getMarca().toUpperCase())+"%")
			.getResultList();
			
			return results;
			
		} catch (NoResultException e) {
			return null;
		}	

	}

	public Carro porCodigo(Long codigo) {
		return manager.find(Carro.class, codigo);
	}
	
	
	public List<Carro> porNomeLista(String marca) {
		return this.manager.createQuery("from Carro " +
				"where upper(marca) like :marca", Carro.class)
				.setParameter("marca", marca.toUpperCase() + "%")
				.getResultList();
	}
	
	public Carro ultiCod() {
	
		return manager.createQuery("codigo from Carro ",Carro.class)
				.getSingleResult();
	} 
	
	public Carro porNome(String marca) {
		try {
			return manager
					.createQuery(
							"from Carro where upper(marca) = :marca",
							Carro.class)
					.setParameter("marca", marca.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Carro> lista() {
		// TODO filtrar apenas func  (por um grupo específico)
		return this.manager.createQuery("from Carro", Carro.class)
				.getResultList();
	}
	
/****************   LISTADO DE LIBRO (INFORME DE LIBRO)  **************************/
	@SuppressWarnings({ "unchecked" })
	public List<Carro> filtradosReportesCarroTeste2(Carro carro,Long codigoInicio, Long codigoFin,String marcaInicio, String marcaFin,String docentInicio, String docentFin, Integer console ) {
		Session session = manager.unwrap(Session.class);
	    Criteria criteriaLib = session.createCriteria(Carro.class)
	    		.createAlias("docente", "doc");
	    System.out.println("console : "+ console);
	    if ((codigoInicio != null)&&(codigoFin != null)){
	  		System.out.println("Entró en codigoRestriccion");
	  		 criteriaLib.add(Restrictions.between("codigo", codigoInicio, codigoFin));
	  		
	    }else {
	    	System.out.println("sino de codigoRestriccion");
	    }
	  
	  
	    if ((StringUtils.isNotBlank(marcaInicio)) && (StringUtils.isNotBlank(marcaFin))){
	  		System.out.println("Entró en marcaRestriccion");
			criteriaLib.add(Restrictions.between("marca", marcaInicio, marcaFin+"z")) ;
			
	    }else {
	    	System.out.println("sino de marcaRestriccion");
	    }
	
	    if ((StringUtils.isNotBlank(docentInicio)) && (StringUtils.isNotBlank(docentFin))){
	    	criteriaLib.add(Restrictions.between("doc.nombre", docentInicio, docentFin+"z"));
	    }else {
	    	System.out.println("sino de docentRestriccion");
	    }
	    
	    if ((console != null)){
	    	  System.out.println("entra console");
	    	  if (console == 1){
	    		  System.out.println("entra 1");
	    		  return criteriaLib.addOrder(Order.asc("codigo")).list();
	  	       }
	    	  if (console == 2){
	    		  System.out.println("entra 2");
	    		  return criteriaLib.addOrder(Order.asc("marca")).list();
		  	  }
	    	  if (console == 3){
	    		  System.out.println("entra 3");
	    		  return criteriaLib.addOrder(Order.asc("doc.nombre")).list();
		  	  }
	    }else {
	    	System.out.println("sino de docentRestriccion");
	    }
	    return criteriaLib.list();
	}
	
/****************************************************************************************/
}
