package com.infoIV.biblioteca.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.jboss.weld.bean.NewBean;

import com.infoIV.biblioteca.model.Config;
import com.infoIV.biblioteca.model.Deuda;
import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.filter.DeudaFilter;
import com.infoIV.biblioteca.util.jpa.Transactional;

/**
 * @author nelfoxxx
 *
 */
public class Deudas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	private DeudaFilter filtro = new DeudaFilter();

	@Inject
	private Configs xconfigs;
	@Inject
	private Lector lector;
	@Inject
	private Deuda deuda;
	@Inject
	private Prestamo pres;
	private List<Deuda> listaPrestamoMorosos;
	private List<Deuda> deudaSeleccionada;
	private Date diaPre;
	private Date diaDev;
	private Date diaRecup;
	private Lector xlector;
	Prestamo xpres;
	@Inject
	private FacesContext facesContext;

	@Transactional
	public void remover(Deuda deuda) {
		System.out.println(deuda.getNum());

		manager.remove(deuda);
		manager.flush();

	}

	/***************
	 * CALCULOS PARA OBTENER LOS DATOS PARA DEUDA
	 * 
	 * @return
	 ************/
	@Transactional
	public void recalcularValorTotal(Deuda deuda, Date diaPre, Date diaDev,
			Date diaRecup, Lector xlector, Prestamo xprestamo2) {

		System.out.println("Entra a recalcular valor total");
		/* Recibo los parametros de pesquisaPrestamo */

		this.xlector = xlector;
		this.xpres = xprestamo2;
		this.diaPre = diaPre;
		this.diaDev = diaDev;
		this.diaRecup = diaRecup;

		Integer subtotal = xconfigs.getCofigsMonto(); /*
													 * estira el costo de la
													 * multa (desde Config)
													 */

		Integer montoDeuda = 0;
		Integer diasAMultar = 0;

		Integer diaMoroso = xconfigs.diaPrestarMasMoroso();/*
															 * cant.dias a
															 * prestar +
															 * cant.dias
															 * tolerables de
															 * retraso
															 */

		/* verificando datos... OK */
		System.out.println("Fecha de Prestamo = " + diaPre);
		System.out.println("Fecha de Recuperacion = " + diaRecup);
		System.out.println("Dia limite de espera = " + diaMoroso);

		/*
		 * si hay o no dias para multar...si hay estira cant.dias a cobrar
		 * interés
		 */
		diasAMultar = this
				.diferenciaFechas(diaPre, diaDev, diaRecup, diaMoroso);

		System.out.println("Cantidad de días a cobrar multa = " + diasAMultar);

		montoDeuda = subtotal * diasAMultar;

		if (montoDeuda > 0) {
			System.out.println("Debe Generar y guardar en Deuda");

			System.out
					.println("Deuda Monto Config desde Deuda = " + montoDeuda);
			System.out.println("entro");

			Integer pagado = 0;

			deuda.setMonto(montoDeuda);
			System.out.println("monto" + deuda.getMonto());
			deuda.setPagado(pagado);
			System.out.println("pagado = " + deuda.getPagado());
			deuda.setLector(xlector);
			System.out.println("lector = " + deuda.getLector().getNombre());
			deuda.setPrestamo(xpres);
			System.out.println("prestamo = " + deuda.getPrestamo().getNumero());

			this.manager.merge(deuda);

		}

	}

	@Transient
	public Integer diferenciaFechas(Date diaPre, Date diaDev, Date diaRecup,
			Integer diaMoroso) {
		/****** CALCULO DE FECHAS *******/
		Integer x = null;
		Date diaLimite;
		Date xfp = diaPre;
		SimpleDateFormat formateador = new SimpleDateFormat();
		System.out.println(formateador.format(xfp));

		Calendar c = GregorianCalendar.getInstance();
		c.setTimeInMillis(xfp.getTime());
		c.add(Calendar.DAY_OF_MONTH, diaMoroso);
		diaLimite = (c.getTime());

		System.out.println("Fecha limite de espera = " + diaLimite);

		if (diaRecup.before(diaLimite)) {
			System.out.println("No genera deuda ");
			x = 0;
		} else {
			System.out.println("Genera deuda");
			/****** CALCULO DE FECHAS *******/

			Date xfp1 = diaRecup;
			SimpleDateFormat formateador1 = new SimpleDateFormat();

			Date xfp2 = diaDev;
			SimpleDateFormat formateador2 = new SimpleDateFormat();

			Calendar c1 = GregorianCalendar.getInstance();
			c1.setTimeInMillis(xfp1.getTime());

			Calendar c2 = GregorianCalendar.getInstance();
			c2.setTimeInMillis(xfp2.getTime());

			long fechaInicialMs = xfp2.getTime();
			long fechaFinalMs = xfp1.getTime();
			long diferencia = fechaFinalMs - fechaInicialMs;
			double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
			x = ((int) dias);

		}
		return x;
	}

	/********************************************************************/
	@SuppressWarnings({ "unchecked" })
	public List<Deuda> filtradosReportesDeudas() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Deuda.class).createAlias(
				"prestamo", "prest");
		criteria.add(Restrictions.eq("prest.numero", deuda.getPrestamo()));
		return criteria.list();
	}

	/**************** PARA CONSULTA DE Deudas - PESQUISA ****************************/
	@SuppressWarnings("unchecked")
	public List<Deuda> filtrados(DeudaFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Deuda.class);
		if (filtro.getNum() != null) {
			criteria.add(Restrictions.ge("num", filtro.getNum()));
		}

		return criteria.list();
	}

	/************************************************************************************/


	@SuppressWarnings({ "unchecked" })
	public List<Deuda> informePorLector(Deuda deuda, Long codigoInicio,
			Long codigoFin) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Deuda.class).createAlias(
				"prestamo", "prest");

		if ((codigoInicio != null) && (codigoFin != null)) {
			System.out.println("Entró en codiRestriccion");
			criteria.add(Restrictions.between("lector_codigo", codigoInicio,
					codigoFin));

		} else {
			System.out.println("sino de codiRestriccion");
		}
	
				return criteria.list();
	}

	/**************** LISTADO DE LIBRO (INFORME DE LIBRO) **************************/
	@SuppressWarnings({ "unchecked" })
	public List<Deuda> filtradosReportesDeudaTeste2(Deuda deuda,
			Long numeroInicio, Long numeroFin, String lectorInicio,
			String lectorFin, Long prestamoInicio, Long prestamoFin,
			Integer console) {
		Session session = manager.unwrap(Session.class);
		Criteria criteriaDeu = session.createCriteria(Deuda.class)
				.createAlias("prestamo", "pres").createAlias("lector", "lec");
		System.out.println("console : " + console);
		if ((numeroInicio != null) && (numeroFin != null)) {
			System.out.println("Entró en codiRestriccion");
			criteriaDeu.add(Restrictions
					.between("num", numeroInicio, numeroFin));

		} else {
			System.out.println("sino de codiRestriccion");
		}

		if ((StringUtils.isNotBlank(lectorInicio))
				&& (StringUtils.isNotBlank(lectorFin))) {
			System.out.println("Entró en descriRestriccion");
			criteriaDeu.add(Restrictions.between("lec.nombre", lectorInicio,
					lectorFin + "z"));

		} else {
			System.out.println("sino de descriRestriccion");
		}

		if ((prestamoInicio) != null && ((prestamoFin) != null)) {
			criteriaDeu.add(Restrictions.between("pres.numero", prestamoInicio,
					prestamoFin + "z"));
		} else {
			System.out.println("sino de editoRestriccion");
		}

		if ((console != null)) {
			System.out.println("entra console");
			if (console == 1) {
				System.out.println("entra 1");
				return criteriaDeu.addOrder(Order.asc("num")).list();
			}
			if (console == 2) {
				System.out.println("entra 2");
				return criteriaDeu.addOrder(Order.asc("lec.nombre")).list();
			}
			if (console == 3) {
				System.out.println("entra 3");
				return criteriaDeu.addOrder(Order.asc("pres.numero")).list();
			}
		} else {
			System.out.println("sino de editoRestriccion");
		}
		return criteriaDeu.list();
	}

	/****************************************************************************************/
	@Transactional
	public Deuda guardar(Deuda deuda) {
		System.out.println("Entra en guardar");
		return this.manager.merge(deuda);
	}

	public Deuda porId(Long num) {
		return this.manager.find(Deuda.class, num);
	}

	public List<Deuda> getListaPrestamoMorosos() {
		return listaPrestamoMorosos;
	}

	public DeudaFilter getFiltro() {
		return filtro;
	}

	public List<Deuda> getDeudaSeleccionada() {
		return deudaSeleccionada;
	}

	public void setDeudaSelecionada(List<Deuda> deudaSeleccionada) {
		this.deudaSeleccionada = deudaSeleccionada;
	}

}