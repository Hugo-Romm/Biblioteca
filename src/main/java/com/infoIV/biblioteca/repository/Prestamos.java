package com.infoIV.biblioteca.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.internal.ast.tree.IsNotNullLogicOperatorNode;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.postgresql.util.GT;

import com.infoIV.biblioteca.model.Deuda;
import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.model.Usuario;
import com.infoIV.biblioteca.model.vo.DataValor;
import com.infoIV.biblioteca.repository.filter.PrestamoFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

/**
 * @author nelfoxxx
 *
 */
public class Prestamos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	private PrestamoFilter filtro = new PrestamoFilter();
	private List<Prestamo> prestamosFiltrados;
	private List<Lector> listaNombreLector;
	@Inject
	private Libro libro;
	@Inject
	private Lector lector;
	@Inject
	private Deuda deuda;
	@Inject
	private Prestamo pres;
	private List<Deuda> listaPrestamoMorosos;

	

	@Inject
	private FacesContext facesContext;
  

	@Transactional
	public void remover(Prestamo prestamo) {
		System.out.println(prestamo.getNumero());
		System.out.println(prestamo.getFecrec());
		if (prestamo.getFecrec() == null) {
			System.out.println("Se puede eliminar");
			prestamo = porId(prestamo.getNumero());
			manager.remove(prestamo);
			manager.flush();
		} else {
			System.out.println("No se elimina");
			throw new NegocioException("Prestamo no puede ser excluído.");

		}

	}

	@SuppressWarnings({ "unchecked" })
	public Map<Date, BigDecimal> valoresTotalesPorFecha(Integer numeroDeDias,
			Usuario creadoPor) {
		Session session = manager.unwrap(Session.class);

		numeroDeDias -= 1;

		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);

		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias,
				dataInicial);

		Criteria criteria = session.createCriteria(Prestamo.class);

		// select date(fecha_creacion) as data, sum(valor_total) as valor
		// from pedido where fecha_creacion >= :dataInicial and vendedor_id =
		// :creadoPor
		// group by date(fecha_creacion)

		criteria.setProjection(
				Projections
						.projectionList()
						.add(Projections.sqlGroupProjection(
								"date(fecha_creacion) as data",
								"date(fecha_creacion)",
								new String[] { "data" },
								new Type[] { StandardBasicTypes.DATE }))
						.add(Projections.sum("valorTotal").as("valor"))).add(
				Restrictions.ge("dataCriacao", dataInicial.getTime()));

		if (creadoPor != null) {
			criteria.add(Restrictions.eq("vendedor", creadoPor));
		}

		List<DataValor> valoresPorData = criteria.setResultTransformer(
				Transformers.aliasToBean(DataValor.class)).list();

		for (DataValor dataValor : valoresPorData) {
			resultado.put(dataValor.getData(), dataValor.getValor());
		}

		return resultado;
	}

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias,
			Calendar dataInicial) {
		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}

		return mapaInicial;
	}
/****************    PARA CONSULTA DE PRESTAMOS - PESQUISA ****************************/
	@SuppressWarnings("unchecked")
	public List<Prestamo> filtrados(PrestamoFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Prestamo.class);
		if (filtro.getNumero() != null) {
			criteria.add(Restrictions.ge("numero", filtro.getNumero()));
		}

		return criteria.list();
	}
/************************************************************************************/
	
	
/***************	VALIDACIONES DE PRESTAMOS *****************/
	@JoinColumn(name = "libro_codigo")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Prestamo> filtradosRestriccion(Prestamo prestamo) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Prestamo.class);

		System.out.println("Libro Codigo = " + prestamo.getLibro().getCodigo());

		System.out.println("Libro Ejemplar = " + prestamo.getNroeje()+"Fecha recuperacion");
		if ((prestamo.getLibro() != null)||(prestamo.getNroeje() != null)) {
			criteria.add(Restrictions.eq("libro", prestamo.getLibro()));
			
				criteria.add(Restrictions.eq("nroeje", prestamo.getNroeje()));
				
			
					criteria.add(Restrictions.isNull("fecrec"));
					
					}
		return criteria.list();
	}
/*******************************************************************/
	
/****************   LISTADO DE PRESTAMO (INFORME DE PRESTAMO)  **************************/

	
	@SuppressWarnings({ "unchecked" })
	public List<Prestamo> filtradosReportesPrestamoTeste2(Prestamo prestamo,Date dataInicio,Date dataFim,String lecInicio, String lecFin,String libInicio, String libFin, Integer console ) {
		Session session = manager.unwrap(Session.class);
	    Criteria criteria = session.createCriteria(Prestamo.class)
	    		.createAlias("lector", "lec")
				.createAlias("libro", "lib");
	   
	    System.out.println(dataInicio);
	    System.out.println(dataFim);
	    if ((dataInicio !=null) && (dataFim !=null)){
	    	System.out.println("Entró en dataRestriccion");
	  	    criteria.add(Restrictions.between("fecpre", dataInicio, dataFim)) ;
	    }else{
	    	System.out.println("sino de dataREstriccion");
	    }
	    if (StringUtils.isNotBlank(lecInicio)){
	  		System.out.println("Entró en lecRestriccion");
			criteria.add(Restrictions.between("lec.nombre", lecInicio, lecFin+"z")) ;
	    }else {
	    	System.out.println("sino de lecRestriccion");
	    }
		
	    if (StringUtils.isNotBlank(libInicio)){
			System.out.println("Entró en LibRestriccion");
			criteria.add(Restrictions.between("lib.descri", libInicio, libFin+"z"));
	    }else {
	    	System.out.println("sino de libRestricciones");
	    }
	    if ((console != null)){
	    	  System.out.println("entra console");
	    	  if (console == 1){
	    		  System.out.println("entra 1");
	    		  return criteria.list();
	  	       }
	    	  if (console == 2){
	    		  System.out.println("entra 2");
	    		  return criteria.add(Restrictions.isNull("fecrec")).list();
		  	  }
	    	  if (console == 3){
	    		  System.out.println("entra 3");
	    		  return criteria.add(Restrictions.isNotNull("fecrec")).list();
		  	  }
	    	  if (console == 4){
	    		  System.out.println("entra 4");
	    		  Criteria criteria1 = session.createCriteria(Deuda.class)
	    				  .createAlias("prestamo", "prest");
	    		  
	    		  criteria1.add(Restrictions.ge("prest.numero", deuda.getPrestamo()));
	    		  return criteria1.list(); 
		  	  }
	    }
	    return criteria.list();
	}
/****************************************************************************************/
	@SuppressWarnings({ "unchecked" })
	public List<Deuda> filtradosReportesDeudas( ) {
		Session session = manager.unwrap(Session.class);
	    Criteria criteria = session.createCriteria(Deuda.class)
	    		.createAlias("prestamo", "prest");
	             criteria.add(Restrictions.eq("prest.numero", deuda.getPrestamo()));
	    return criteria.list();
	}
	
	
	public Prestamo guardar(Prestamo prestamo) {
		return this.manager.merge(prestamo);
	}

	@Transactional
	public Deuda guardarDeuda(Deuda deuda) {
		return this.manager.merge(deuda);
	}

	public Prestamo porId(Long numero) {
		return this.manager.find(Prestamo.class, numero);
	}

	public List<Deuda> getListaPrestamoMorosos() {
		return listaPrestamoMorosos;
	}
	public List<Prestamo> getPrestamosFiltrados() {
		return prestamosFiltrados;
	}
	public List<Lector> getListaNombreLector() {
		return listaNombreLector;
	}
	public PrestamoFilter getFiltro() {
		return filtro;
	}
	
	
}